/**
 * Created by breno on 02/06/15.
 */
$(document).ready(function() {
    var nomes = ["#analista-table","#notificador-table","#captador-table"];

    nomes.forEach(function(element, index, total){
        console.log(element);
        var elementName = element.substr(1);
        $(element).dataTable({
            "dom": '<"span12"<"row-fluid datatables-top"<"span4"l><"span4" f><"span4 btnAdicionar'+elementName+'">><"row-fluid datatables-middle"t><"row-fluid datatables-bottom"ip>>',
            "sPaginationType": "full_numbers",
            "columnDefs": [
                { sWidth : "400px", "targets": [0,1] },
                { sWidth : "200px", targets:2 }
            ],
            "columns" : [
                { "className": "center-important" },
                { "className": "center-important" },
                { "className": "center-important" }
            ]
        });
        var btnAdicionar = $('#btnAdicionarOrigin-'+elementName);
        $('div.btnAdicionar'+elementName).html(btnAdicionar.html());
        btnAdicionar.html("");
    });
});