/**
 * Created by breno on 02/06/15.
 */

(function buildTable(table){
    table.DataTable({
        "dom":'<"span12"<"row-fluid"<"pull-left"l><"pull-right"f>><"row-fluid datatables-middle"t><"row-fluid datatables-bottom"ip>>',
        "sPaginationType": "full_numbers"
    });
})($('#causas-table'));