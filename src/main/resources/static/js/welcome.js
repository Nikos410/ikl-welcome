// Slideshow
var displayedSlide;
var hiddenSlide;

var displayedToast;
var hiddenToast;

function getNextImage() {
    $.ajax({
        url: '/nextimage',
        type: "GET",
        data: null,
        dataType: 'json',
        success: function (data) {
            hiddenSlide.attr("src", "/images/" + data.file);
            hiddenToast.find(".toast-body").text(data.info);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log("Request received status code " + xhr.status + ". Error: " + thrownError);
            hiddenSlide.attr("src", "/assets/fallback.jpg");
            hiddenToast.find(".toast-body").text(null);
        }
    });
}

function displayNextSlide() {
    // Swap images
    var tempSlide = hiddenSlide;
    hiddenSlide = displayedSlide;
    displayedSlide = tempSlide;

    // Hide previous slide
    hiddenSlide.css("display", "none");
    // Show the other slide
    displayedSlide.css("display", "inline");

    // Swap toasts
    var tempToast = hiddenToast;
    hiddenToast = displayedToast;
    displayedToast = tempToast;

    // Hide previous toast
    hiddenToast.css("display", "none");
    // Show the other toast if it hast text
    var info = displayedToast.find(".toast-body").text();
    if (info != null && info != "") {
        displayedToast.css("display", "inline");
        displayedToast.toast('show');
    }

    // Update image for hidden slide
    getNextImage();
}

// News section
var headlineElement;
var introElement;
var contentElement;

var nextHeadline = "Wird geladen...";
var nextIntro = "Hier könnte Ihre Werbung stehen!";
var nextContent;

var lastId = -1;

var converter = new showdown.Converter();

function displayNextNews() {
    var parsedContent = converter.makeHtml(nextContent);
    headlineElement.text(nextHeadline);
    introElement.text(nextIntro);
    contentElement.html(parsedContent);

    loadNextNews();
}

function loadNextNews() {
    $.ajax({
        url: '/nextnews/' + lastId,
        type: "GET",
        data: null,
        dataType: 'json',
        success: function (data) {
            nextHeadline = data.headline;
            nextIntro = data.introduction;
            nextContent = data.content;
            lastId = data.id;
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log("Request received status code " + xhr.status + ". Error: " + thrownError);
        }
    });
}

// Initialize welcome screen
$(document).ready(function () {
    // Initialize toasts
    $('.toast').toast();

    // Initialize elements for slideshow
    displayedSlide = $("#slideA");
    hiddenSlide = $("#slideB");

    displayedToast = $('#infoA');
    hiddenToast = $('#infoB');

    // Initialize elements for news section
    headlineElement = $("#newsHeadline");
    introElement = $("#newsIntro");
    contentElement = $("#newsContent");

    // Start slideshow and news section
    displayNextSlide();
    displayNextNews();

    setInterval(displayNextSlide, 4000);
    setInterval(displayNextNews, 4000);
});
