$('document').ready(function () {
    $('#dsCard').hide();
    $("#btn-payment").prop("disabled", true);

    $.fn.serializeObject = function () {
        const o = {};
        const a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    $('#customerUtilityAccount').on('blur', function (event) {
        event.preventDefault();

        const cardNo = $(this).val();
        const href = 'http://localhost:6070/api/v1/' + cardNo;

        $.get(href, function (res, status) {
            if (res.successful) {
                $('#dsCard').show();
                $('#cardNo').html(cardNo);
                $('#cardName').html(res.responseDetails);
                $('#customerName').val(res.responseDetails)
                $("#smartCard").css({"border": "2px solid #94e894"});
                $('#msg').html('');
                $("#btn-payment").prop("disabled", false);
            } else {
                $("#btn-payment").prop("disabled", true);
                $('#smartCard').val(cardNo);
                $("#smartCard").css({"border": "2px solid orangered"});
                $('#dsCard').hide();
                $('#msg').html('Could not validate smart Card: ' + cardNo);
                clearFields();
            }
        });
    });

    $("#months").change(function () {
        calculateAmount();
    });
    $("#bouquet").change(function () {
        if ($(this).val() !== "") {
            calculateAmount();
        } else {
            $("#btn-payment").prop("disabled", true);
            clearFields();
        }
    });

    $(":checkbox").change(function () {
        calculateAmount();
    });

    const calculateAmount = function () {
        console.log('calculating amount');
        console.log(JSON.stringify($("#payment-form").serializeObject()));
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: "http://localhost:6070/api/v1/calculateAmount",
            dataType: "json",
            data: JSON.stringify($("#payment-form").serializeObject()),
            success: function (data, textStatus, jqXHR) {
                $("#amount").val(data.amount + data.commission);
                $("#charge").val(data.commission);
                $("#addOn").val(data.addonFee)
                $('#dsp-amount').val(data.amount).val();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log('Posting error: ' + textStatus);
            }
        });
    }

    $('#btn-payment').click(function () {
        $("#actions").hide();
        //  $("#confirmModal").hide();
      //  console.log(JSON.stringify($("#payment-form").serializeObject()));
     //   const data = JSON.stringify($("#payment-form").serializeObject());

        const data = formToJSON();
        console.log(data);
        $('#page-stats').html('<img src="/assets/img/loading.gif" style="max-height: 60px; max-width: 60px;text-align: center; color: #6baa6b;" alt=""/> Processing Payment, Please wait ...');
        addPayment(data);
        return false;
    });

    function formToJSON() {
        return JSON.stringify({
            "customerUtilityAccount":$('#customerUtilityAccount').val(),
            "amount": $('#amount').html(),
            "methodOfPayment": 'cash',
            "currency": 'USD',
            "customerName": $('#customerName').html(),
            "merchantId": $('#merchantId').val(),
            "customerPaymentDetails1": $('#customerPaymentDetails1').val(),
            "customerPaymentDetails3": $('#customerPaymentDetails3').html(),
            "customerPaymentDetails4": $('#customerPaymentDetails4').html(),
            "customerPaymentDetails5": $('#customerPaymentDetails5').html()

        });
    }

    function addPayment(data) {
        console.log('addPayment');
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: "http://localhost:6070/api/v1/post",
            dataType: "json",
            data: data,
            success: function (data) {
                if (data.status === 'Rejected') {
                    alert('Transaction Failed: ' + data.narrative);
                } else {
                    $('#page-stats').html('');
                    clearFields();
                    $("#paymentReference").html(data.id);
                    document.location.href = 'http://localhost:6070/api/v1/ref?id='+data.id;
                  //  $("#paymentReference").html(data.reference);
                   // alert('Transaction Successful: ' + data.narrative);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('Posting error: ' + textStatus);
            }
        });
    }


    $('#btn-reset').on('click', function (event) {
        $('#msg').html('');
        $('#smartCard').val('');
        $("#smartCard").css({"border": "1px solid grey"});
        clearFields();
    });

    const clearFields = function () {
        $('#dsCard').hide();
        $('#customerName').val('');
        $('#bouquet').val('');
        $('#months').val(1);
        $('#dsp-amount').val('0.0');
        $('#charge').val('3.0');
        $('#addOn').val('0.0');
        $('#amount').val('3.0');
        $('#page-stats').html('');
        $("#btn-payment").prop("disabled", true);
    };
});