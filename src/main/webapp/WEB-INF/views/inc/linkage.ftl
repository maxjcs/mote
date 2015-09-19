<#macro linkage cityVO pageVO>
<div class="form-group">
    <label class="control-label">省份</label>
    <select id="provinceSelect" class="form-control w100" name="provinceId">
    </select>
</div>
<div class="form-group">
    <label class="control-label">城市</label>
    <select id="citySelect" class="form-control w100" name="cityId">
    </select>
</div>
<script>
    var cityData = ${cityVO};
    var allVal = [{
        "id": -1,
        "name": "全部",
        "pid": -1,
        "children": [{"id": -1, "name": "全部", "pid": -1, "children": null}]
    }];
    for (var i = 0; i < cityData.length; i++) {
        cityData[i].children.unshift(allVal[0].children[0]);
    }
    var mergeCityData = allVal.concat(cityData);
    citySelector({
        data: mergeCityData,       //省份城市数据
        pid: 'provinceSelect',     //省份select标签的id
        cid: 'citySelect',         //城市select标签的id
        defaultPid:${pageVO.provinceId?default(-1)},         //默认显示的省份id(数据中的省份id)
        defaultCid:${pageVO.cityId?default(-1)}              //默认显示的城市id(数据中的城市id)
    });
</script>
</#macro>