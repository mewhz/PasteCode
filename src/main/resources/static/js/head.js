$(document).ready(()=>{
    $.getJSON("/head", (result)=>{
        console.log(result);
        $.each(result, (i, temp)=>{
            $("h1").append(temp + " ");
        });
    })
});