

// for (var i = 0; i <5; i++) {
//     $.ajax({
//         type: 'post',
//         dataType: 'json',
//         url: CTX + "/users/update",
//         data: {
//             age: 2000 + i
//         },
//         success: function (result) {
//
//         },
//         error: function () {
//
//         }
//     });
// }


// /**
//  * Created by heyanjing on 2017/12/18 8:38.
//  */
// var arr = [];
// arr.push("a");
// arr.push("b");
// arr.push("c");
// $.ajax({
//     type: 'post',
//     dataType: 'json',
//     url: CTX + "/list",
//     data: {
//         params: arr
//     },
//     success: function (result) {
//
//     },
//     error: function () {
//
//     }
// });
// var persons = [], result = [];
// for (var i = 0; i < 3; i++) {
//     var obj = {};
//     obj.name = "name" + i;
//     obj.age = i;
//     obj.birthday = "1989-09-19";
//     persons.push(obj);
// }
// Globle.buildParams("persons", persons, result);
// $.ajax({
//     type: 'post',
//     dataType: 'json',
//     url: CTX + "/persons",
//     data: result.join("&"),
//     success: function (result) {
//
//     },
//     error: function () {
//
//     }
// });
// var map = {};
// map.a = "a";
// map.b = "b";
// map.c = "c";
// $.ajax({
//     type: 'post',
//     dataType: 'json',
//     url: CTX + "/map",
//     data: map,
//     success: function (result) {
//
//     },
//     error: function () {
//
//     }
// });
// var str = "persons[0][name]=何彦静";
//
// console.log(str);
// console.log(str.replace(/\[([a-zA-Z]+)]/gi, function (str, a, b, c, e, f, g) {
//     // console.log(str)//[name]
//     // console.log(a)// name
//     // console.log(b)// 10
//     // console.log(c)// persons[0][name][age]=何彦静
//     return "." + a;
// }));
// console.log(str.replace(/\[([a-zA-Z]+)]/gi, ".$1"));
//
// var img = document.getElementById("img1"), canvas1 = document.getElementById("canvas1");
// canvas1.style.backgroundColor = "cornsilk";
// canvas1.height = img.height;
// canvas1.width = img.width;
// var ctx1 = canvas1.getContext("2d");
// ctx1.drawImage(img, 0, 0, img.width, img.height);
// var url = canvas1.toDataURL();
// var newImg = document.createElement("img");
// newImg.style.backgroundColor = "cornsilk";
// newImg.src = url;
// document.body.appendChild(newImg);
// // $.ajax({
// //     type: 'post',
// //     dataType: 'json',
// //     url: CTX + "/code/base64Img",
// //     data: {
// //         base64Img: url
// //     },
// //     success: function (result) {
// //         console.log(result);
// //     },
// //     error: function () {
// //
// //     }
// // });
// // $.ajax({
// //     type: 'get',
// //     dataType: 'json',
// //     url:"http://www.timingbar.com/static/kaptcha.jpg",
// //     success: function (result) {
// //         console.log(result);
// //     },
// //     error: function () {
// //
// //     }
// // });
//
// //
// // test();
// // function test() {
// //     var canvas = document.getElementById("canvas4");
// //     var url = canvas.toDataURL();
// //
// //     var newImg = document.createElement("img");
// //     newImg.src = url;
// //     document.body.appendChild(newImg);
// // }
// //
// //
// // var img = document.getElementById("img1"), canvas1 = document.getElementById("canvas1"), canvas2 = document.getElementById("canvas2"), canvas3 = document.getElementById("canvas3"), canvas4 = document.getElementById("canvas4");
// // canvas1.style.backgroundColor = "cornsilk";
// // canvas2.style.backgroundColor = "cornsilk";
// // canvas3.style.backgroundColor = "cornsilk";
// // canvas4.style.backgroundColor = "cornsilk";
// // var ctx1 = canvas1.getContext("2d");
// // var ctx2 = canvas2.getContext("2d");
// // var ctx3 = canvas3.getContext("2d");
// // ctx1.drawImage(img, 0, 0, img.width, img.height);
// // // ctx2.drawImage(img, 0, 0, img.width, img.height);
// // // ctx3.drawImage(img, 0, 0, img.width, img.height);
// // var WIDTH = img.width, HEIGHT = img.height;
// // var imgData = ctx1.getImageData(0, 0, WIDTH, HEIGHT);
// // console.log(imgData);
// // var newImgData = toHex(imgData);
// // console.log(newImgData);
// // // ctx1.putImageData(newImgData, 0, 0);
// //
// // /**
// //  *
// //  *任何颜色都由红、绿、蓝三基色组成，假如原来某点的颜色为RGB(R，G，B)，那么，我们可以通过下面几种方法，将其转换为灰度：
// //  1.浮点算法：Gray=R*0.3+G*0.59+B*0.11
// //  2.整数方法：Gray=(R*30+G*59+B*11)/100
// //  3.移位方法：Gray =(R*77+G*151+B*28)>>8;
// //  4.平均值法：Gray=（R+G+B）/3;
// //  5.仅取绿色：Gray=G；
// //  */
// // function toHex(imgData) {//二值化图像
// //     var data = imgData.data;//描述了一个一维数组，包含以 RGBA 顺序的数据，数据使用  0 至 255（包含）的整数表示。
// //     console.log(data.length);
// //     var greyAve = 0;
// //     for (var j = 0; j < WIDTH * HEIGHT; j++) {
// //         var r = data[4 * j];
// //         var g = data[4 * j + 1];
// //         var b = data[4 * j + 2];
// //         greyAve += r * 0.3 + g * 0.59 + b * 0.11;
// //     }
// //     greyAve /= WIDTH * HEIGHT;//计算平均灰度值。
// //     for (j = 0; j < WIDTH * HEIGHT; j++) {
// //         r = data[4 * j];
// //         g = data[4 * j + 1];
// //         b = data[4 * j + 2];
// //         var grey = (r + g + b) / 3; // r * 0.333 + g * 0.333 + b * 0.333;//取平均值。
// //         grey = grey > greyAve ? 255 : 0;
// //         data[4 * j] = grey;
// //         data[4 * j + 1] = grey;
// //         data[4 * j + 2] = grey;
// //     }
// //     return imgData;
// // }
// // var xyArr = toXY(newImgData);
// // console.log(xyArr);
// // //二值化图像
// // function toXY(imgData) {
// //     var result = new Array(HEIGHT);
// //     var data = imgData.data;
// //     for (var j = 0; j < HEIGHT; j++) {
// //         result[j] = new Array(WIDTH);
// //         for (var k = 0; k < WIDTH; k++) {
// //             var r = data[4 * (j * WIDTH + k)];
// //             var g = data[4 * (j * WIDTH + k) + 1];
// //             var b = data[4 * (j * WIDTH + k) + 2];
// //
// //             result[j][k] = (r + g + b) > 500 ? 0 : 1;//赋值0、1给内部数组 0代表白色,1代表黑色
// //         }
// //     }
// //     return result;
// // }//图像转数组
// // var newXyArr = corrode(xyArr);
// // newXyArr = expand(newXyArr);
// // console.log(newXyArr);
// // var newData = xx(newXyArr);
// // newImgData.data=newData;
// // console.log(newImgData);
// // // ctx1.putImageData(newImgData, 0, 0);
// //
// //
// // function xx(newXyArr) {
// //     var result=[];
// //     for(var i=0;i<newXyArr.length;i++){
// //         var newXyArr2 = newXyArr[i];
// //         for(var j=0;j<newXyArr2.length;j++){
// //             var number = newXyArr2[j]===0?255:0;
// //             result.push(number);
// //             result.push(number);
// //             result.push(number);
// //             result.push(255);
// //         }
// //     }
// //     return result;
// // }
// // function corrode(xyArr) {
// //     for (var j = 1; j < xyArr.length - 1; j++) {
// //         for (var k = 1; k < xyArr[j].length - 1; k++) {
// //             if (xyArr[j][k] == 1 && xyArr[j - 1][k] + xyArr[j + 1][k] + xyArr[j][k - 1] + xyArr[j][k + 1] == 0) {
// //                 xyArr[j][k] = 0;
// //             }
// //         }
// //     }
// //     return xyArr;
// // }//腐蚀（简单）
// //
// //
// //
// // function expand(xyArr) {
// //     for (var j = 1; j < xyArr.length - 1; j++) {
// //         for (var k = 1; k < xyArr[j].length - 1; k++) {
// //             if (xyArr[j][k] == 0 && xyArr[j - 1][k] + xyArr[j + 1][k] + xyArr[j][k - 1] + xyArr[j][k + 1] == 4) {
// //                 xyArr[j][k] = 1;
// //             }
// //         }
// //     }
// //     return xyArr;
// // }//膨胀（简单）
// //
// // var splited = split(newXyArr,4);
// // console.log(splited);
// // var newData = fromXY(splited);
// // ctx1.putImageData( fromXY(splited), 0, 0);
// //
// // function fromXY(fromArray){
// //     var fromImgData = ctx1.createImageData(WIDTH,HEIGHT);
// //     var fromPixelData = fromImgData.data;
// //     for(var j=0;j<fromArray.length;j++){
// //         for(var k=0;k<fromArray[j].length;k++){
// //             var innergrey = (fromArray[j][k]==1?0:255);
// //             fromPixelData[4*(j*WIDTH+k)] = innergrey;
// //             fromPixelData[4*(j*WIDTH+k)+1] = innergrey;
// //             fromPixelData[4*(j*WIDTH+k)+2] = innergrey;
// //             fromPixelData[4*(j*WIDTH+k)+3] = 255;
// //         }
// //     }
// //     return fromImgData;
// // }//数组转图像
// // function split(fromArray,count){
// //     var numNow = 0;
// //     var status = false;
// //     var w = fromArray[0].length;
// //     for(var k=0;k<w;k++) {//遍历图像
// //         var sumUp = 0;
// //         for (var j=0;j<fromArray.length;j++) //检测整列是否有图像
// //             sumUp += fromArray[j][k];
// //         if(sumUp == 0){//切割
// //             for (j=0;j<fromArray.length-1;j++)
// //                 fromArray[j].splice(k,1);
// //             w --;
// //             k --;
// //             status = false;
// //             continue;
// //         } else {//切换状态
// //             if(!status)
// //                 numNow ++;
// //             status = true;
// //         }
// //         // if(numNow!=count){//不是想要的数字
// //         //     for (j=0;j<fromArray.length-1;j++)
// //         //         fromArray[j].splice(k,1);
// //         //     w --;
// //         //     k --;
// //         // }
// //     }
// //     return fromArray;
// // }//切割，获取特定数字
// //
// //
// // function zoomToFit(fromArray) {
// //     var imgD = fromXY(fromArray);
// //     var w = lastWidth;
// //     var h = lastHeight;
// //     var tempc1 = document.createElement("canvas");
// //     var tempc2 = document.createElement("canvas");
// //     tempc1.width = fromArray[0].length;
// //     tempc1.height = fromArray.length;
// //     tempc2.width = w;
// //     tempc2.height = h;
// //     var tempt1 = tempc1.getContext("2d");
// //     var tempt2 = tempc2.getContext("2d");
// //     tempt1.putImageData(imgD, 0, 0, 0, 0, tempc1.width, tempc1.height);
// //     tempt2.drawImage(tempc1, 0, 0, w, h);
// //     var returnImageD = tempt2.getImageData(0, 0, WIDTH, HEIGHT);
// //     fromArray = toXY(returnImageD);
// //     fromArray.length = h;
// //     for (var i = 0; i < h; i++)
// //         fromArray[i].length = w;
// //     return fromArray;
// // }//尺寸归一化