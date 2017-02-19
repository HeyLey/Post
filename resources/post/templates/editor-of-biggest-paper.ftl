<!doctype html>

<html>
<head>
    <meta charset="utf-8">
    <title>ФИО редактора газеты с самым большим тиражем в данной типографии</title>
    <link rel="stylesheet" href="/css/styles.css">

    <script type="text/javascript" src="/scripts/jquery-3.1.1.js"></script>
</head>

<body>
<div class="top-div">
    <h1>ФИО редактора газеты с самым большим тиражем в данной типографии</h1>

    <div class="data-div">
        <form method="post" action="/post/editor-of-biggest-paper">
            <label>
                Типография:
                <select name="typography">
                    <option value="-" selected>- Выберите типографиию -</option>
                <#list typography as id, name>
                    <option value="${id}">${name}</option>
                </#list>
                </select>
            </label>
            <br/>
            <input type="submit" value="Показать">
        </form>
    <#if table??>
    ${table}
    </#if>

    </div>
</div>
</body>
</html>