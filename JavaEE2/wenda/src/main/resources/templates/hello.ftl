<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
</head>
<body>
    <#list maps?keys as key>
        ${key} : ${maps[key]}
    </#list>
    <br>

    <#assign var=77/>
    <#--
        <#assign haha=66/>
    -->
    ${var!0}
    <br>
    ${haha!0}
    <br>

    <#if haha??>
        存在
    <#else>
        不存在
    </#if>
</body>
</html>