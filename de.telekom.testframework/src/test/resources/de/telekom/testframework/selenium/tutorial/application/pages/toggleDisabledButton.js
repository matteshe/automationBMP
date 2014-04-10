function realToogle() {
    var element = document.getElementsByName("disabledField")[0];
    element.disabled = !element.disabled;
}
function toggleDisabledField() {
    window.setTimeout("realToogle()", 5000);
    
}

