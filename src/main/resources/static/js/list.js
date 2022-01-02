import {copy, getQueryVariable} from './utils.js'

$(document).ready(()=>{
    let all = $("#all");
    let body = $("body");
    let set = new Set();
    let id = getQueryVariable("id");

    $.ajax({
        url: "/selectList?id=" + id,
        success: (result) => {
            console.log(result.type);
            let len = result.type.length;
            let type = result.type;
            let date = result.date;
            let time_id = result.time_id;
            let text = result.text;
            let remark = result.remark;
            for (let i = 0; i < len; i ++){
                all.append("<details><summary>"+ date[i] +" " + time_id[i] + " " + remark[i] + "</summary>" +
                    "<pre><code class='language-"+ type[i] +"'>"+ text[i] +"</code></pre></details>");
                if (!set.has(type[i])){
                    switch (type[i]){
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
                    set.add(type[i]);
                }
            }
            Prism.highlightAll();
        }
    })
});
