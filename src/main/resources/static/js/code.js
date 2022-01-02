import {copy, getQueryVariable} from './utils.js'

$(document).ready(()=>{

    $(".button").click((e)=>{

        let id = $(e.target).attr("id");
        // 单击事件复制
        copy(id);
    });

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
            console.log(result.type[0]);
            switch (result.type[0]){
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
