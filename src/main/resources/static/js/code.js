function copy(){
    let clipboard = new ClipboardJS('#button');
    clipboard.on('success', function(e) {
        e.clearSelection();
        clipboard.destroy();
        // 释放内存，如果不释放，下次总会多复制
        toastr.success("复制成功，快去粘贴吧！");
    });

    clipboard.on('error', function(e) {
        toastr.error("一键复制失败，请手动复制！")
    });
}

function getQueryVariable(variable){

    let query = window.location.search.substring(1);
    let vars = query.split("&");
    for (let i = 0; i < vars.length; i ++){
        let pair = vars[i].split("=");
        if (pair[0] === variable)  return pair[1];
    }
    return false;
}

$(document).ready(()=>{
    let id = getQueryVariable("id");
    const codeId = $("#code");
    const title = $("title");
    const body = $("body");
    $.ajax({
        url: "/selectCode?id=" + id,
        success: (result)=>{
            codeId.attr("class","language-" + result.type);
            codeId.text(result.text);
            title.text(result.remark);
            if (result.remark === "")   {
                title.text("贴代码");
            }
            switch (result.type){
                case "java":
                    body.append('<script src="js/prism-java.min.js"></script>');
                    break;
                case "cpp":
                    body.append('<script src="js/prism-c.min.js"></script>');
                    body.append('<script src="js/prism-cpp.min.js"></script>');
                    break;
                case "python":
                    body.append('<script src="js/prism-python.min.js"></script>');
            }
            // 对代码重新渲染
            Prism.highlightAll();
        }
    })
});
