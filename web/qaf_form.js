/*
 * Global Javascript functions.
 */

// Actions ----------------------------------------------------------------------------------------

/**
 * Set focus on the element of the given id.
 * @param id The id of the element to set focus on.
 */
function setFocus(id) {
    var element = document.getElementById(id);
    if (element && element.focus) {
        element.focus();
    }
}

/**
 * Set highlight on the elements of the given ids. It basically sets the classname of the elements
 * to 'highlight'. This require at least a CSS style class '.highlight'.
 * @param ids The ids of the elements to be highlighted, comma separated.
 */
function setHighlight(ids) {
    var idsArray = ids.split(",");
    for (var i = 0; i < idsArray.length; i++) {
        var element = document.getElementById(idsArray[i]);
        if (element) {
            element.className = 'highlight';
        }
    }
}

function cancel() { window.location("/manage")};