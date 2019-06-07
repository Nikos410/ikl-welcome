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
    var input = document.getElementById("image-info-" + id);
    var info = input.value;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", "/admin/images/" + id + "/setinfo", true);
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4) {
            if (xmlHttp.status == 200) {
                $('#image-info-' + id).popover('show');

                setTimeout(function(){
                    $('#image-info-' + id).popover('hide');
                }, 2000);
            } else {
                alert("Could not set info. Status " + xmlHttp.status);
            }
        }
    };
    xmlHttp.send("info=" + info);
}

function deleteImage(id) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", "/admin/images/" + id + "/delete", true);
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.status == 200) {
            // Remove the deleted element
            var toRemove = document.getElementById("image-card-" + id);
            toRemove.parentNode.removeChild(toRemove);
        }
        else {
            alert("Could not delete image. Status " + xmlHttp.status);
        }
    };
    xmlHttp.send(null);
}
