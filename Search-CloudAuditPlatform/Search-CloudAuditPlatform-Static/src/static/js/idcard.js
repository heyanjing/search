var Idcard = {
    /** 最低年限 */
    MIN: 1930,
    /** 中国公民身份证号码最小长度。 */
    CHINA_ID_MIN_LENGTH: 15,
    /** 中国公民身份证号码最大长度。 */
    CHINA_ID_MAX_LENGTH: 18,
    power: [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2],
    cityCodes: {"11": "北京", "12": "天津", "13": "河北", "14": "山西", "15": "内蒙古", "21": "辽宁", "22": "吉林", "23": "黑龙江 ", "31": "上海", "32": "江苏", "33": "浙江", "34": "安徽", "35": "福建", "36": "江西", "37": "山东", "41": "河南", "42": "湖北 ", "43": "湖南", "44": "广东", "45": "广西", "46": "海南", "50": "重庆", "51": "四川", "52": "贵州", "53": "云南", "54": "西藏 ", "61": "陕西", "62": "甘肃", "63": "青海", "64": "宁夏", "65": "新疆", "71": "台湾", "81": "香港", "82": "澳门", "91": "国外"},
    twFirstCode: {"A": 10, "B": 11, "C": 12, "D": 13, "E": 14, "F": 15, "G": 16, "H": 17, "J": 18, "K": 19, "L": 20, "M": 21, "N": 22, "P": 23, "Q": 24, "R": 25, "S": 26, "T": 27, "U": 28, "V": 29, "X": 30, "Y": 31, "W": 32, "Z": 33, "I": 34, "O": 35},
    /**验证香港身份证号码*/
    isValidHK: function (idcard) {
        idcard = idcard.replace(/\(|\)/g, "");
        var sum = 0;
        if (idcard.length === 9) {
            sum = (idcard.substring(0, 1).toUpperCase().charCodeAt(0) - 55) * 9 + (idcard.substring(1, 2).toUpperCase().charCodeAt() - 55) * 8;
            idcard = idcard.substring(1, 9);
        } else {
            sum = 522 + (idcard.substring(0, 1).toUpperCase().charCodeAt(0) - 55) * 8;
        }
        var mid = idcard.substring(1, 7);
        var end = idcard.substring(7, 8);
        var iflag = 7;
        for (var i = 0; i < mid.length; i++) {
            sum += parseInt(mid.charAt(i)) * iflag;
            iflag--;
        }
        if (end.toUpperCase() === "A") {
            sum += 10;
        } else {
            sum = sum + parseInt(end);
        }
        return (sum % 11 === 0);
    },
    /**验证台湾身份证号码*/
    isValidTW: function (idcard) {
        var start = idcard.substring(0, 1).toUpperCase(), mid = idcard.substring(1, 9), end = idcard.substring(9, 10), iStart = twFirstCode[start], sum = parseInt(iStart / 10) + (iStart % 10) * 9, iflag = 8;
        for (var i = 0; i < mid.length; i++) {
            sum += parseInt(mid.charAt(i)) * iflag;
            iflag--;
        }
        return (sum % 10 === 0 ? 0 : (10 - sum % 10)) === parseInt(end);
    },
    /** 验证10位身份编码是否合法*/
    isValid10: function (idcard) {
        var me = this, info = new Array(3), card = idcard.replace(/\(|\)/g, "");
        if (card.length !== 8 && card.length !== 9 && idcard.length !== 10) {
            return false;
        }
        if (/^[a-zA-Z][0-9]{9}$/.test(idcard)) {
            // 台湾
            info[0] = "台湾";
            var char2 = idcard.substring(1, 2);
            if (char2 === "1") {
                info[1] = "M";
            } else if (char2 === "2") {
                info[1] = "F";
            } else {
                info[1] = "N";
                info[2] = false;
                return info;
            }
            info[2] = !!me.isValidTW(idcard);
        } else if (/^[1|5|7][0-9]{6}\(?[0-9A-Z]\)?$/.test(idcard)) {
            // 澳门
            info[0] = "澳门";
            info[1] = "N";
        } else if (/^[A-Z]{1,2}[0-9]{6}\(?[0-9A]\)?$/.test(idcard)) { // 香港
            info[0] = "香港";
            info[1] = "N";
            info[2] = !!me.isValidHK(idcard);
        } else {
            return false;
        }
        return info;
    },

    /**15位转18位*/
    convert15To18: function (idcard) {
        var me = this, idCard18 = "";
        if (idcard.length !== me.CHINA_ID_MIN_LENGTH) {
            return false;
        }
        if (/^\d{15}$/.test(idcard)) {
            // 获取出生年月日
            var sYear = idcard.substring(6, 8);
            sYear = "19" + sYear;
            idCard18 = idcard.substring(0, 6) + sYear + idcard.substring(8);
            // 获取数字数组
            var iArr = [];
            for (var i = 0; i < idCard18.length; i++) {
                iArr.push(parseInt(idCard18.charAt(i)));
            }
            var iSum17 = me.getPowerSum(iArr);
            var iCode18 = me.getCheckCode18(iSum17);
            if (iCode18.length > 0) {
                idCard18 += iCode18;
            } else {
                return false;
            }
        } else {
            return false;
        }
        return idCard18;
    },
    /** 验证小于当前日期 是否有效* @return 是否有效*/
    isValidDate: function (iYear, iMonth, iDate) {
        var me = this, now = new Date(), year = now.getFullYear(), datePerMonth;
        if (iYear < me.MIN || iYear >= year) {
            return false;
        }
        if (iMonth < 1 || iMonth > 12) {
            return false;
        }
        switch (iMonth) {
            case 4:
            case 6:
            case 9:
            case 11:
                datePerMonth = 30;
                break;
            case 2:
                var dm = ((iYear % 4 === 0 && iYear % 100 !== 0) || (iYear % 400 === 0)) && (iYear > MIN && iYear < year);
                datePerMonth = dm ? 29 : 28;
                break;
            default:
                datePerMonth = 31;
        }
        return (iDate >= 1) && (iDate <= datePerMonth);
    },
    /**验证15位身份编码是否合法*/
    isValid15: function (idcard) {
        var me = this;
        if (idcard.length !== me.CHINA_ID_MIN_LENGTH) {
            return false;
        }
        if (/^\d{15}$/.test(idcard)) {
            var proCode = idcard.substring(0, 2);
            if (me.cityCodes[proCode]) {
                return false;
            }
            var birthCode = idcard.substring(6, 12);
            birthCode = "19" + birthCode;
            var fullyear = birthCode.substring(0, 4);
            var month = birthCode.substring(4, 6);
            var day = birthCode.substring(6, 8);
            if (!me.isValidDate(parseInt(fullyear), parseInt(month), parseInt(day))) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    },
    /**将getPowerSum的和与11取模获得校验码*/
    getPowerSum: function (charArr) {
        var me = this, iSum17 = 0;
        if (me.power.length === charArr.length) {
            for (var i = 0; i < charArr.length; i++) {
                for (var j = 0; j < me.power.length; j++) {
                    if (i === j) {
                        iSum17 += charArr[i] * me.power[j];
                    }
                }
            }
        }
        return iSum17;
    },
    /**将getPowerSum的和与11取模获得校验码*/
    getCheckCode18: function (iSum17) {
        var iCode18 = "";
        switch (iSum17 % 11) {
            case 10:
                iCode18 = "2";
                break;
            case 9:
                iCode18 = "3";
                break;
            case 8:
                iCode18 = "4";
                break;
            case 7:
                iCode18 = "5";
                break;
            case 6:
                iCode18 = "6";
                break;
            case 5:
                iCode18 = "7";
                break;
            case 4:
                iCode18 = "8";
                break;
            case 3:
                iCode18 = "9";
                break;
            case 2:
                iCode18 = "X";
                break;
            case 1:
                iCode18 = "0";
                break;
            case 0:
                iCode18 = "1";
                break;
        }
        return iCode18;
    },
    /** 验证18位身份编码是否合法*/
    isValid18: function (idcard) {
        var me = this, bTrue = false;
        if (idcard.length === me.CHINA_ID_MAX_LENGTH) {
            // 前17位
            var code17 = idcard.substring(0, 17);
            // 第18位
            var code18 = idcard.substring(17, me.CHINA_ID_MAX_LENGTH).toUpperCase();
            if (/^\d{17}$/.test(code17)) {
                var charArr = [];
                for (var i = 0; i < code17.length; i++) {
                    charArr.push(parseInt(code17.charAt(i)));
                }
                var iSum17 = me.getPowerSum(charArr);
                var iCode18 = me.getCheckCode18(iSum17);
                if (iCode18.length > 0) {
                    if (iCode18 === code18) {
                        bTrue = true;
                    }
                }
            }
        }
        return bTrue;
    },
    /**验证身份证是否合法*/
    isValid: function (idcard) {
        var me = this;
        if (idcard) {
            idcard = idcard.replace(/(^\s*)|(\s*$)/g, '');
            if (me.isValid18(idcard)) {
                return true;
            }
            if (me.isValid15(idcard)) {
                return true;
            }
            var idcards = me.isValid10(idcard);
            if (idcards) {
                if (idcards[2]) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
};


