<!doctype html>

<html>
<head>
    <meta charset="utf-8">
    <title>Номера отделений почты и наименования газет, поступающих в количесве меньше чем заданное</title>
    <link rel="stylesheet" href="/css/styles.css">

    <script type="text/javascript" src="/scripts/jquery-3.1.1.js"></script>
</head>

<body>
<div class="top-div">
    <h1>Номера отделений почты и наименования газет, поступающих в количесве меньше чем заданное</h1>

    <div class="data-div">
        <form method="post" action="/post/papers-with-low-number">
            <label>
                Количество:
                <input id="number" type="text" name="number" value="0">
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