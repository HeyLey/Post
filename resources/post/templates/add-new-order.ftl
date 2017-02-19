<!doctype html>

<html>
<head>
    <meta charset="utf-8">
    <title>Добавить новый заказ</title>
    <link rel="stylesheet" href="/css/styles.css">

    <script type="text/javascript" src="/scripts/jquery-3.1.1.js"></script>
</head>

<body>
<div class="top-div">
    <h1>Добавить новый заказ</h1>

    <div class="data-div">
        <form method="post" action="/post/add-new-order">
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
                Тираж:
                <input id="number" type="text" name="number" value="0">
            </label>
            <br/>
            <input type="submit" value="Добавить заказ">
        </form>
    </div>
</div>
</body>
</html>