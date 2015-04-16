(function() {
    $('.datepicker').datepicker( {language: "pt-BR", format: "dd/mm/yyyy"} ).on('changeDate', function (ev) {
        $(this).datepicker('hide');
    });
}).call();