function basedOnSelectionColorize(collapsibleHeaderId) {
    var collapsibleHeader = document.getElementById(collapsibleHeaderId)
    var classList = collapsibleHeader.classList;
    var selectedDateClass = "selectedDate"

    if (classList.contains(selectedDateClass)) classList.remove(selectedDateClass)
    else classList.add(selectedDateClass)
}