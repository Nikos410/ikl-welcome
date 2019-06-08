// Used to format markdown
var converter = new showdown.Converter();

// Edit an article
function editArticle(id) {
    var card = $("#allArticles").find("[data-article-id='" + id + "']");

    var data = {
        "headline": card.children("input[name='headline']").val(),
        "introduction": card.children("input[name='intro']").val(),
        "content": card.find("textarea[name='content']").val()
    };

    console.log(data);

    $.ajax({
        url: "/admin/news/" + id + "/edit",
        type: "POST",
        data: data,
        success: function () {
            var button = card.find("button");
            button.popover('show');

            setTimeout(function(){
                button.popover('hide');
            }, 2000);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert("Could not set info. Request received status code " + xhr.status + ". Error: " + thrownError);
        }
    });
}

// Initialize everything
$(document).ready(function () {
    // Display formatted text live
    $('textarea').each(function (index, value) {
        $(this).bind('input propertychange', function () {
            var resultDiv = $(this).parent().find('.jumbotron');
            var resultHtml = converter.makeHtml($(this).val());
            resultDiv.html(resultHtml);
        });
    });

    // Format content initially
    $('textarea').each(function (index, value) {
        var resultDiv = $(this).parent().find('.jumbotron');
        var resultHtml = converter.makeHtml($(this).val());
        resultDiv.html(resultHtml);

    });

    // Initialize popovers
    $('[data-toggle="popover-manual"]').popover({
        trigger: 'manual'
    });
});
