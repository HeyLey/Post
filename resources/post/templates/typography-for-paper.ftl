<!doctype html>

<html>
<head>
    <meta charset="utf-8">
    <title>Адреса типографий, по которым печатается данная газета</title>
    <link rel="stylesheet" href="/css/styles.css">

    <script type="text/javascript" src="/scripts/jquery-3.1.1.js"></script>
</head>

<body>
<div class="top-div">
    <h1>Адреса типографий, по которым печатается данная газета</h1>

    <div class="data-div">
        <form method="post" action="/post/typography-for-paper">
            <label>
                Газета
                <select name="paper">
                    <option value="-" selected>- Выберите газету -</option>
                <#list papers as id, name>
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