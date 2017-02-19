<!doctype html>

<html>
<head>
    <meta charset="utf-8">
    <title>Изменить количество газет, поступающих в отделение</title>
    <link rel="stylesheet" href="/css/styles.css">

    <script type="text/javascript" src="/scripts/jquery-3.1.1.js"></script>
</head>

<body>
<div class="top-div">
    <h1>Изменить количество газет, поступающих в отделение</h1>

    <div class="data-div">
        <form method="post" action="/post/change-papers-number">
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
            <label>
                Типография
                <select name="typography">
                    <option value="-" selected>- Выберите типографиию -</option>
                <#list typography as id, name>
                    <option value="${id}">${name}</option>
                </#list>
                </select>
            </label>
            <br/>
            <label>
                Отделение почты
                <select name="post">
                    <option value="-" selected>- Выберите отделение -</option>
                <#list post as id, name>
                    <option value="${id}">${name}</option>
                </#list>
                </select>
            </label>
            <br/>
            <label>
                Количество
                <input id="number" type="text" name="number" value="">
            </label>
            <br/>
            <input type="submit" value="Изменить заказ">
        </form>
    </div>
</div>
</body>
</html>