document.write("<script language=javascript src='/static/js/dyn/edit-common.js'><\/script>");

$(function () {
    $("div.controls textarea").css("height", "100px");

    /* ============= extract =============  */
    $("div.hfform#dyn_mysql_binlogQueryForm .box-content #sourceColumns").css("width","500px");
    $("div.hfform#dyn_mysql_scanQueryForm .box-content #sourceColumns").css("width","500px");
    $("div.hfform#dyn_mysql_scan_moreQueryForm .box-content #sourceColumns")
        .after($('<a class="btn btn-success" style="margin-right:5px;" ' +
            'href="javascript:$(\'#sourceDB\').attr(\'last-value\',\'\');$(\'#sourceDB\').change();"><i class=\'icon-refresh\'></i></a>'));


    /* ============= transform =============  */
    $("div.hflist#dyn_prune_trans_list .box-content h2")
        .append($('<a class="btn" href="javascript:refreshAndAddNewRow(\'sourceColumns\', \'sourceColumn\', \'targetColumn\');">刷新</a>'))
        .append($('<span style="font-size:14px;">(</span>'))
        .append($('<i class="icon-hand-right"></i>'))
        .append($('<span style="font-size:  14px;"> 编辑列表时请点击刷新按钮，如果存在灰色背景行数据表明来源字段可能不存在，慎重处理！)</span>'));

    $("div.hflist#dyn_agg_trans_list .box-content h2")
        .append($('<a class="btn" href="javascript:refreshSelectOptions(\'sourceColumns\', \'bucketFields\', true);refreshSelectOptions(\'sourceColumns\', \'bucketTimeField\');refreshSelectOptions(\'sourceColumns\', \'metricFields\');">刷新</a>'))
        .append($('<span style="font-size:14px;">(</span>'))
        .append($('<i class="icon-hand-right"></i>'))
        .append($('<span style="font-size:  14px;"> 编辑列表时请点击刷新按钮，如果存在灰色背景行数据表明来源字段可能不存在，慎重处理！)</span>'));

    $("div.hflist#dyn_agg_trans_list .box-content .hflist-data td.center").append('<a class="btn btn-success hfhref" href="javascript:void(0)" params="" action="{&quot;component.row.copy&quot;:{&quot;param&quot;:&quot;{}&quot;}}"><i class="icon-copy"></i></a>');

    /* ============= load =============  */
    $("div.hfform#dyn_kafka_producerQueryForm .box-content h2")
        .append($('<a class="btn" href="javascript:refreshSelectOptions(\'sourceColumns\', \'partitionField\');">刷新</a>'))
        .append($('<span style="font-size:14px;">(</span>'))
        .append($('<i class="icon-hand-right"></i>'))
        .append($('<span style="font-size:  14px;"> 编辑列表时请点击刷新按钮，如果存在灰色背景行数据表明来源字段可能不存在，慎重处理！)</span>'));

    $("div.hfform#dyn_mysql_writeQueryForm [name=targetTable]").attr("auto_add_option", "auto_add_option");

    refreshSelectOptions('sourceColumns', 'bucketFields');

});