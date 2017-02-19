<!doctype html>

<html>
<head>
    <meta charset="utf-8">
    <title>Отчет по газете</title>
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
                $('#name').text(paper['name']);
                $('#index').text(paper['index']);
                $('#price').text(paper['price']);
            }
        }

    }

</script>
<div class="top-div">
    <h1>Справка по газете</h1>
    <label>
        <select name="paper" onchange="selectPaper(this.value)">
            <option value="-" selected>- Выберите газету -</option>
        <#list papers as id, name>
            <option value="${id}">${name}</option>
        </#list>
        </select>
    </label>
    <br/>

    <div class="data-div">
        Газета:
        <div id="name" class="value"></div>
        <br/>
        Индекс:
        <div id="index" class="value"></div>
        <br/>
        Цена:
        <div id="price" class="value"></div>
        <br/>
    </div>
</div>
</body>
</html>