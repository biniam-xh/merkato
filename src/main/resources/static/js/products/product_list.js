// Material Select Initialization
$(document).ready(function() {
    //$('.mdb-select').materialSelect();
    var selected =1;
    $(".select-quantity").change(function () {
        selected = $(this).val();
    });

    $(".add-to-cart").click(function(){
        product_id = $(this).attr("data");


        var contextRoot = "/" + window.location.pathname.split('/')[1];
        $.ajax({
            url: contextRoot+"/addtocart",
            contentType: 'application/json',
            dataType: 'json',
            type: "post",
            data: JSON.stringify({product_id: product_id, quantity: selected}),
            success: function(data){
                console.log(data.productList)
            },
            error: function (error) {
                console.log('eror========================================')
                console.log(error);
                alert(error)
            }
            }

        );
    });
});