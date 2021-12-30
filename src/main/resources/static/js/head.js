

$(document).ready(()=>{
    $.getJSON("/codes?id=1637767613075", (result)=>{
        console.log(result);
        console.log(result.type);
        $("#code").attr("class","language-java");
        $("#code").text(result.text);
        $("title").text(result.remark);
        Prism.highlightAll();
    })
});