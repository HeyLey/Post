<!doctype html>

<html>
<head>
    <meta charset="utf-8">
    <title>Изменить газету</title>
    <link rel="stylesheet" href="/css/styles.css">

    <script type="text/javascript" src="/scripts/jquery-3.1.1.js"></script>
</head>

<body>

<script>

    var papers = [
    ${papers_data}];

    function selectPaper(id) {
        for (var i = 0; i < papers.length; i++) {
            var paper = papers[i];
            if (paper['id'] == id) {
                $('#index').val(paper['index']);
                $('#price').val(paper['price']);
            }
        }

    }

</script>

<div class="top-div">
    <h1>Изменить газету</h1>
    <div class="data-div">
        <form method="post" action="/post/change-paper">
            <label>
                <select name="paper" onchange="selectPaper(this.value)">
                    <option value="-" selected>- Выберите газету -</option>
                <#list papers as id, name>
                    <option value="${id}">${name}</option>
                </#list>
                </select>
            </label>
            <br/>
            <label>
                Индекс:
                <input id="index" type="text" name="index" value="">
            </label>
            <br/>
            <label>
                Цена:
                <input id="price" type="text" name="price" value="">
            </label>
            <br/>
            <input type="submit" value="Изменить">

        </form>
    </div>
</div>
</body>
</html>