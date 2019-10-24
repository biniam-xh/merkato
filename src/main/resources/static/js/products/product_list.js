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

    $("#complete-order").prop("disabled",true);

    function occurrences(string, substring){
        var n=0;
        var pos=0;

        while(true){
            pos=string.indexOf(substring,pos);
            if(pos!=-1){ n++; pos+=substring.length;}
            else{break;}
        }
        return(n);
    }
    isSameAddress = false;

    $(".address").change(function () {
        address = $("#address").val();
        address2 = $("#address2").val();

        var count = occurrences(address,",");
        var count2 = occurrences(address2,",");

        if(count >= 2 && isSameAddress || (count >= 2 && count2 >= 2)){
            $("#invalid-feedback").hide();
            $("#invalid-feedback2").hide();
            $("#complete-order").prop("disabled",false);
        }

        else{
            if(count<2){
                $("#invalid-feedback").show();
            }
            if(count2 < 2 && !isSameAddress){
                $("#invalid-feedback2").show();
            }

            $("#complete-order").prop("disabled",true);

        }
    });



    $("#same-address").change(function() {
        // this will contain a reference to the checkbox
        if (this.checked) {
            //$("#address").val($("#address").val())
            $("#billingAddressContainer").addClass("d-none");
            $("#billingAddressContainer").removeClass("d-block");
            isSameAddress = true;
        } else {
            //$("#address").val('')
            $("#billingAddressContainer").removeClass("d-none");
            $("#billingAddressContainer").addClass("d-block");
            isSameAddress = false;
        }

    });

    $("#complete-order").click(function (event) {
        alert("test")

        order_id = $("#orderId").val();
        address = $("#address").val();
        if(isSameAddress){
            address2 = address;
        }
        else{
            address2 = $("#address2").val();
        }

        var contextRoot = "/" + window.location.pathname.split('/')[1];
        $.ajax({
                url: contextRoot+ "/checkout/billing?billingAddress="+ address2 + "&shippingAddress="+address+"&orderId="+order_id,
                contentType: 'application/json',
                dataType: 'json',
                type: "get",
                success: function(data){
                    console.log(data)
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

    // $("form#ratingForm").submit(function(e)
    // {
    //     e.preventDefault(); // prevent the default click action from being performed
    //     if ($("#ratingForm :radio:checked").length == 0) {
    //         $('#status').html("nothing checked");
    //         return false;
    //     } else {
    //         $('#status').html( 'You picked ' + $('input:radio[name=rating]:checked').val() );
    //     }
    // });

    $("#add-review").click(function(e){
        e.preventDefault();
        console.log(JSON.stringify( $("#ratingForm").serialize() ));

        alert("sd")
        $.ajax({
                url: "/products/review/add",
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


    $(".follow").click(function(){
        user_id = $("#userId").attr("data");
        btnId = $(this).attr("data");

        $.ajax({
                url: "/products/user/follow/"+user_id,
                contentType: 'application/json',
                dataType: 'json',
                type: "put",
                success: function(data){
                    console.log(data);
                    $(".follow"+btnId).addClass('d-none');
                    $(".follow"+btnId).removeClass('d-inline-block');
                    $(".unfollow"+btnId).addClass('d-inline-block');
                },
                error: function (error) {
                    console.log('error========================================')
                    console.log(error);
                }
            }
        );
    });


    $(".unfollow").click(function(){
        user_id = $("#userId").attr("data");
        btnId = $(this).attr("data");

        $.ajax({
                url: "/products/user/unfollow/"+user_id,
                contentType: 'application/json',
                dataType: 'json',
                type: "put",
                success: function(data){
                    console.log(data);
                    $(".follow"+btnId).addClass('d-inline-block');
                    $(".unfollow"+btnId).addClass('d-none');
                    $(".unfollow"+btnId).removeClass('d-inline-block')
                },
                error: function (error) {
                    console.log('error========================================')
                    console.log(error);
                }
            }
        );
    });



});