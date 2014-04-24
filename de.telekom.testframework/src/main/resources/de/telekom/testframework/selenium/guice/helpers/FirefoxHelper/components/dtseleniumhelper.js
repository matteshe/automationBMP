Components.utils.import("resource://gre/modules/XPCOMUtils.jsm");

function LOG(text)
{
    var consoleService = Components.classes["@mozilla.org/consoleservice;1"].getService(Components.interfaces.nsIConsoleService);
    consoleService.logStringMessage(text);
}

headerName = "X-Selenium";
headerValue = "sonne";

function DTSeleniumHelper() {
}
DTSeleniumHelper.prototype = {
    classID: Components.ID("{9cf5f3df-2505-42dd-9094-c1631bd1be1c}"),
    contractID: "@dt/dtseleniumhelper;1",
    QueryInterface: XPCOMUtils.generateQI([Components.interfaces.nsIObserver, Components.interfaces.nsISupports]),
    observe: function(subject, topic, data)
    {
        if (topic === "http-on-modify-request") {
            var httpChannel = subject.QueryInterface(Components.interfaces.nsIHttpChannel);
            httpChannel.setRequestHeader(headerName, headerValue, false);
            return;
        }


        if (topic === "profile-after-change") {

            LOG("-> install DTSeleniumHelper");

            var os = Components.classes["@mozilla.org/observer-service;1"]
                    .getService(Components.interfaces.nsIObserverService);

            os.addObserver(this, "http-on-modify-request", false);
            return;
        }
    }
};

NSGetFactory = XPCOMUtils.generateNSGetFactory([DTSeleniumHelper]);
