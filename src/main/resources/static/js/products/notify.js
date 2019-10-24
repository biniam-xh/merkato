function sendAction(event) {
    // alert("in send action");
    event.preventDefault();
    $.ajax({
        url: "/addProductEvent",
        type: "POST"
    });
    return;
}


$(document).ready(function() {

    $("#notifyUserAddProduct").on("click", sendAction);

});