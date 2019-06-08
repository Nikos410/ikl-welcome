// Configure Dropzone
Dropzone.options.imageDropzone = {
    maxFilesize: 24, // MB
    timeout: 240000, // ms
    acceptedFiles: "image/*"
};

// Enable popovers
$(document).ready(function(){
    $('[data-toggle="popover"]').popover({
        trigger: 'hover'
    });

    $('[data-toggle="popover-manual"]').popover({
        trigger: 'manual'
    });
});

function setInfo(id) {
    var input = $("#image-info-" + id);

    $.ajax({
        url: "/admin/images/" + id + "/setinfo",
        type: "POST",
        data: {"info": input.val()},
        success: function () {
            input.popover('show');

            setTimeout(function(){
                input.popover('hide');
            }, 2000);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert("Could not set info. Request received status code " + xhr.status + ". Error: " + thrownError);
        }
    });
}

function deleteImage(id) {
    $.ajax({
        url: "/admin/images/" + id + "/delete",
        type: "POST",
        success: function () {
            // Remove the deleted element
            $("#image-card-" + id).remove();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert("Could not delete image. Request received status code " + xhr.status + ". Error: " + thrownError);
        }
    });
}
