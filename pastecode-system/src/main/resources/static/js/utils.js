export function copy(){
    let clipboard = new ClipboardJS('.button');
    clipboard.on('success', (e)=>{
        e.clearSelection();
        // 释放内存，否则会多复制
        clipboard.destroy();
        toastr.success("复制成功,快去站贴吧！");
    });

    clipboard.on('error', (e)=>{
        toastr.error("一键复制失败，请手动复制！");
    })
}
export function getQueryVariable(variable){
    let query = window.location.search.substring(1);
    let vars = query.split("&");
    for (let i = 0; i < vars.length; i ++){
        let pair = vars[i].split("=");
        if (pair[0] === variable)   return pair[1];
    }
    return false;
}
