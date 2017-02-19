<!doctype html>

<html>
<head>
    <meta charset="utf-8">
    <title>Открыть / закрыть типографию</title>
    <link rel="stylesheet" href="/css/styles.css">

    <script type="text/javascript" src="/scripts/jquery-3.1.1.js"></script>
</head>

<body>
<div class="top-div">
    <h1>Открыть / закрыть типографию</h1>

    <div class="data-div">
        <form method="post" action="/post/open-close-typography">
            <label>
                Типография:
                <select name="typography">
                    <option value="-" selected>- Выберите типографию -</option>
                <#list typography as id, name>
                    <option value="${id}">${name}</option>
                </#list>
                </select>
            </label>
            <br/>
            <button type="submit" name="action" value="open">Открыть</button>
            <button type="submit" name="action" value="close">Закрыть</button>

        </form>
    </div>
</div>
</body>
</html>