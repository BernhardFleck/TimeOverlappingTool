window.onload = showParticipationLink()

function showParticipationLink() {
    var linkElement = document.getElementById("participationLink")
    linkElement.innerHTML = getParticipationLink()
}

function getParticipationLink() {
    var surveyId = document.getElementById("surveyId").textContent
    return getDomainFromURL() + "/survey/participate/" + surveyId
}

function getDomainFromURL() {
    return window.location.origin
}