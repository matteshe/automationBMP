function toggleDisabledField() {
    var element = document.getElementsByName("delayedDisabledText")[0];
    element.disabled = !element.disabled;
}
function delayedToggleDisabledField() {
    window.setTimeout(toggleDisabledField, 5000);
    
}

