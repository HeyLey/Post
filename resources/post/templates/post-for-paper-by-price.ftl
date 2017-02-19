<!doctype html>

<html>
<head>
    <meta charset="utf-8">
    <title>На какие адреса почтовых отделений поступает газета с ценой больше данной</title>
    <link rel="stylesheet" href="/css/styles.css">

    <script type="text/javascript" src="/scripts/jquery-3.1.1.js"></script>
</head>

<body>
<div class="top-div">
    <h1>На какие адреса почтовых отделений поступает газета с ценой больше данной</h1>

    <div class="data-div">
        <form method="post" action="/post/post-for-paper-by-price">
            <label>
                Цена:
                <input id="price" type="text" name="price" value="0">
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