function gfn_isNull(str) {
	if (str == null) return true;
	if (str == "NaN") return true;
	if (new String(str).valueOf() == "undefined") return true;
    var chkStr = new String(str);
    if( chkStr.valueOf() == "undefined" ) return true;
    if (chkStr == null) return true;
    if (chkStr.toString().length == 0 ) return true;
    return false;
}

function ComSubmit(opt_formId) {
	this.formId = gfn_isNull(opt_formId) == true ? "commonForm" : opt_formId;
	this.url = "";

	if(this.formId == "commonForm"){
		$("#commonForm")[0].reset();
		$("#commonForm").empty();
	}

	this.setUrl = function setUrl(url){
		this.url = url;
	};

	this.addParam = function addParam(key, value){
		$("#"+this.formId).append($("<input type='hidden' name='"+key+"' id='"+key+"' value='"+value+"' >"));
	};

	this.submit = function submit(){
		var frm = $("#"+this.formId)[0];
		frm.action = this.url;
		frm.method = "post";
		frm.submit();
	};
}

var gfv_ajaxCallback = "";
function ComAjax(opt_formId){
	this.url = "";
	this.formId = gfn_isNull(opt_formId) == true ? "commonForm" : opt_formId;
	this.param = "";

	if(this.formId == "commonForm"){
		$("#commonForm")[0].reset();
		$("#commonForm").empty();
	}

	this.setUrl = function setUrl(url){
		this.url = url;
	};

	this.setCallback = function setCallback(callBack){
		fv_ajaxCallback = callBack;
	};

	this.addParam = function addParam(key,value){
		this.param = this.param + "&" + key + "=" + value;
	};

	this.ajax = function ajax(){
		if(this.formId != "commonForm"){
			this.param += "&" + $("#" + this.formId).serialize();
		}
		$.ajax({
			url : this.url,
			type : "POST",
			data : this.param,
			async : false,
			success : function(data, status) {
				if(typeof(fv_ajaxCallback) == "function"){
					fv_ajaxCallback(data);
				}
				else {
					eval(fv_ajaxCallback + "(data);");
				}
			}
		});
	};
}

/*
divId : 페이징 태그가 그려질 div
pageIndx : 현재 페이지 위치가 저장될 input 태그 id
recordCount : 페이지당 레코드 수
totalCount : 전체 조회 건수
eventName : 페이징 하단의 숫자 등의 버튼이 클릭되었을 때 호출될 함수 이름
*/
var gfv_pageIndex = null;
var gfv_eventName = null;
function gfn_renderPaging(params){
	var divId = params.divId; //페이징이 그려질 div id
	gfv_pageIndex = params.pageIndex; //현재 위치가 저장될 input 태그
	var totalCount = params.totalCount; //전체 조회 건수
	var currentIndex = $("#"+params.pageIndex).val(); //현재 위치
	if($("#"+params.pageIndex).length == 0 || gfn_isNull(currentIndex) == true){
		currentIndex = 1;
	}

	var recordCount = params.recordCount; //페이지당 레코드 수
	if(gfn_isNull(recordCount) == true){
		recordCount = 10;
	}

	var totalIndexCount = Math.ceil(totalCount / recordCount); // 전체 인덱스 수
	gfv_eventName = params.eventName;

	$("#"+divId).empty();
	var preStr = "";
	var postStr = "";
	var str = "";

	var first = (parseInt((currentIndex-1) / 10) * 10) + 1;
	var last = (parseInt(totalIndexCount/10) == parseInt(currentIndex/10)) ? totalIndexCount%10 : 10;
	var prev = (parseInt((currentIndex-1)/10)*10) - 9 > 0 ? (parseInt((currentIndex-1)/10)*10) - 9 : 1;
	var next = (parseInt((currentIndex-1)/10)+1) * 10 + 1 < totalIndexCount ? (parseInt((currentIndex-1)/10)+1) * 10 + 1 : totalIndexCount;

	console.log('recordCount : '+ recordCount);
	console.log('totalIndexCount : '+ totalIndexCount);
	console.log('first : '+ first);
	console.log('last : '+ last);

	if(totalIndexCount > 10){ //전체 인덱스가 10이 넘을 경우, 맨앞, 앞 태그 작성
		preStr += "<a href='#this' class='pad_5' onclick='_movePage(1)'>[<<]</a>" +
				"<a href='#this' class='pad_5' onclick='_movePage("+prev+")'>[<]</a>";
	}
	else if(totalIndexCount <=10 && totalIndexCount > 1){ //전체 인덱스가 10보다 작을경우, 맨앞 태그 작성
		preStr += "<a href='#this' class='pad_5' onclick='_movePage(1)'>[<<]</a>";
	}

	if(totalIndexCount > 10){ //전체 인덱스가 10이 넘을 경우, 맨뒤, 뒤 태그 작성
		postStr += "<a href='#this' class='pad_5' onclick='_movePage("+next+")'>[>]</a>" +
					"<a href='#this' class='pad_5' onclick='_movePage("+totalIndexCount+")'>[>>]</a>";
	}
	else if(totalIndexCount <=10 && totalIndexCount > 1){ //전체 인덱스가 10보다 작을경우, 맨뒤 태그 작성
		postStr += "<a href='#this' class='pad_5' onclick='_movePage("+totalIndexCount+")'>[>>]</a>";
	}

	for(var i=first; i<(first+last); i++){
		if(i != currentIndex){
			str += "<a href='#this' class='pad_5' onclick='_movePage("+i+")'>"+i+"</a>";
		}
		else{
			str += "<strong><a href='#this' class='pad_5' onclick='_movePage("+i+")'>"+i+"</a></strong>";
		}
	}
	$("#"+divId).append(preStr + str + postStr);
}

function _movePage(value){
	$("#"+gfv_pageIndex).val(value);
	if(typeof(gfv_eventName) == "function"){
		gfv_eventName(value);
	}
	else {
		eval(gfv_eventName + "(value);");
	}
}

//IE 브라우저에서 WAV 파일 실행
function playWavFileInIE(src){
	console.log(src);
	var agent = navigator.userAgent.toLowerCase(); // 브라우져 종류 확인
	
	if((navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || (agent.indexOf("msie") != -1)){ // ie
		 console.log("익스플로러 브라우져 입니다!");
		var origPlayer = document.getElementById('currentWavPlayer');
		if(origPlayer){
			origPlayer.src = '';
			origPlayer.outerHtml = '';
			document.body.removeChild(origPlayer);
			delete origPlayer;
		}
		var newPlayer = document.createElement('bgsound');
		newPlayer.setAttribute('id', 'currentWavPlayer');
		newPlayer.setAttribute('src', src);
		document.body.appendChild(newPlayer);
		return false;
	} else { // chrome, firefox, safafi....
		
		if (agent.indexOf("safari") != -1) {
			console.log("사파리 브라우저입니다.");
		} else if(agent.indexOf("chrome") != -1) {
			console.log("크롬 브라우저입니다.");
		} else if(agent.indexOf("firefox") != -1){
			console.log("파이어 폭스 브라우저입니다.");
		}
		var audio = new Audio(src);
		audio.play();
	}
}