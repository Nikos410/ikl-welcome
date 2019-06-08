var converter = new showdown.Converter();

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
});

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
            alert("Success!");
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert("Could not set info. Request received status code " + xhr.status + ". Error: " + thrownError);
        }
    });
}
