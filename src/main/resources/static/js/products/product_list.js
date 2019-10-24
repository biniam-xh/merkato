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
                console.log(data);
                $(".cartCount").text(data)
                $(".cartCount").html(data);

            },
            error: function (error) {
                console.log('error========================================')
                console.log(error);

            }
            }

        );
    });


    $(".delete-items").click(function(){
        product_id = $(this).attr("data");

        var contextRoot = "/" + window.location.pathname.split('/')[1];
        $.ajax({
                url: contextRoot+"/deleteItems/"+product_id,
                contentType: 'application/json',
                dataType: 'json',
                type: "delete",
                success: function(data){
                    console.log(data)
                },
                error: function (error) {
                    console.log('error========================================')
                    console.log(error);

                }
            }

        );
        $("#card"+product_id).hide();
    });


    $(".edit-cart").click(function(){
        product_id = $(this).attr("data");

        var contextRoot = "/" + window.location.pathname.split('/')[1];
        $.ajax({
                url: contextRoot+"/editcart/"+product_id+"/"+ selected,
                contentType: 'application/json',
                dataType: 'json',
                type: "put",
                success: function(data){
                    console.log(data);
                    $(".cartCount").html(data)
                },
                error: function (error) {
                    console.log('error========================================')
                    console.log(error);

                }
            }

        );
    });

    var orderStatus = 1;
    $(".select-status").change(function () {
        orderStatus = $(this).val();
        alert(orderStatus)
    });

    $("#complete-order").click(function (event) {
        order_id = $("#orderId").attr("data");
        address = $("#address").val();

        var forms = document.getElementsByClassName('needs-validation');

        // Loop over them and prevent submission
        if (forms.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        forms.classList.add('was-validated');

        var contextRoot = "/" + window.location.pathname.split('/')[1];
        $.ajax({
                url: contextRoot+"/checkout/billingAddress?billingAddress="+ address +"&order_id="+order_id,
                contentType: 'application/json',
                dataType: 'json',
                type: "put",
                success: function(data){
                    alert('test');
                },
                error: function (error) {
                    console.log('error========================================')
                    console.log(error);

                }
            }

        );

    });

    $("#generate-pdf").click(function () {
        doc.save('a4.pdf');
    });

    $("#cancel-order").click(function () {
        var contextRoot = "/" + window.location.pathname.split('/')[1];
        order_id = $(this).attr("data");
        //alert(order_id)
        window.location.href = contextRoot + "/cancelOrder?orderId="+order_id;
    });

    $

    $("form#ratingForm").submit(function(e)
    {
        e.preventDefault(); // prevent the default click action from being performed
        if ($("#ratingForm :radio:checked").length == 0) {
            $('#status').html("nothing checked");
            return false;
        } else {
            $('#status').html( 'You picked ' + $('input:radio[name=rating]:checked').val() );
        }
    });

    $(".add-review").click(function(e){
        e.preventDefault();
        var contextRoot = "/" + window.location.pathname.split('/')[1];
        $.ajax({
                url: contextRoot+"/addtocart",
                contentType: 'application/json',
                dataType: 'json',
                type: "post",
                data: JSON.stringify($("form#ratingForm").serialize()),
                success: function(data){
                    console.log(data);

                },
                error: function (error) {
                    console.log('error========================================')
                    console.log(error);

                }
            }

        );
    });



});