function refreshSelectOptions(triggerColumn, compareColumn, multi) {
    if(!multi){
        multi = false;
    }
    var sourceValues = getTargetSelectedValues($("#" + triggerColumn + ":first"));

    if(sourceValues && sourceValues.length > 0){
        var options = ["<option value=''>--请选择--</option>"];
        for(var i in sourceValues) {
            options.push("<option value='" + sourceValues[i] + "'>" + sourceValues[i]  + "</option>")
        }
        var $options = $(options.join(""));

        $("select[name=" + compareColumn + "]").each(function(){
            var $select = $(this);
            var thisVal = $select.val();
            $select.html($options.clone());
            $select.val(thisVal);
        });

        if($("input[name=" + compareColumn + "]").length > 0) {
            var $select = $("<select id='" + compareColumn + "' name='" + compareColumn + "' class='input-xlarge'></select>");
            $select.append($options);
            $("input[name=" + compareColumn + "]").each(function(){
                var selectInit = multi || $select.find("option[value='" + $(this).val() + "']").length > 0;
                if(selectInit) {
                    var _$select = $select.clone();
                    _$select.attr("data-value", $(this).val());
                    _$select.val(_$select.attr("data-value"));
                    $(this).replaceWith(_$select);
                    $(this).css("background", "");
                    if(multi) {
                        _$select.attr("multiple", "multiple");
                        _$select.val(_$select.attr("data-value").split(","));
                        _$select.chosen();//设置为selectx
                        _$select.trigger("chosen:updated");
                    }
                }else {
                    $(this).css("background", "#eee");
                }
            });
        }
    }
}


function refreshAndAddNewRow(triggerColumn, compareColumn, mappingColumn){

    var sourceValues = getTargetSelectedValues($("#" + triggerColumn + ":first"));
    if(sourceValues && sourceValues.length > 0){
        var targetValues = getTargetElementsValues($("input[name=" + compareColumn + "]"));
        var additionalArray = calAdditionalArray(sourceValues, targetValues);
        var addRows = additionalArray[0];
        var delRows = additionalArray[1];
        for(var i in addRows){
            var addValue = addRows[i];
            var $curRow = $("input[name=" + compareColumn + "]:last").parents("tr:first");
            var $newRow = $($curRow).clone();
            $newRow.find("input,select").val("");
            $newRow.css("background", "");
            $newRow.find("input[name=" + compareColumn + "]").val(addValue);
            $newRow.find("input[name=" + mappingColumn + "]").val(addValue);
            $($curRow).after($newRow);
        }
        $("input[name=" + compareColumn + "]").each(function(){
            $(this).parents("tr:first").find("input:visible, select:visible").css("background", "");
        });
        for(var i in delRows){
            $("input[name=" + compareColumn + "]").each(function(){
                if($(this).val() == delRows[i]){
                    $(this).parents("tr:first").find("input:visible, select:visible").css("background", "#eee");
                }
            });
        }
    }

}

function getTargetSelectedValues($targetSelect){
    if($targetSelect.length == 1){
        var targetValues = $targetSelect.val();
        if(!targetValues){
            targetValues = [];
            $targetSelect.children("option").each(function(){
                targetValues.push([$(this).val()]);
            });
        }else if(typeof(targetValues) == 'string'){
            targetValues = targetValues.split(",")
        }

        return targetValues;
    }
    return null;
}


function getTargetElementsValues($targets){
    var values = [];
    if($targets && $targets.length > 0){
        $targets.each(function(){
            values.push($(this).val());
        });
    }
    return values;
}


function calAdditionalArray(arr1, arr2){
    if(!arr1) arr1 = [];
    if(!arr2) arr2 = [];
    var ret1 = [];
    for(var i in arr1){
        if(arr2.indexOf(arr1[i]) < 0){
            ret1.push(arr1[i]);
        }else {
            arr2.splice(arr2.indexOf(arr1[i]),1);
        }
    }
    return [ret1, arr2];
}