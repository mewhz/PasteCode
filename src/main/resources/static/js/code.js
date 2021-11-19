// $(document).ready(()=>{
//     $("#button").click(()=>{
//         let text = $("#code").text();
//         navigator.clipboard.writeText(text);
//         toastr.success("复制成功，快去粘贴吧！");
//     });
// });
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
