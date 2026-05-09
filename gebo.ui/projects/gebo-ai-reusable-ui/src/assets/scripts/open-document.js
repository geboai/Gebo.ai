function geboOpenDocumentJS(url, token, iframeElement, contentType) {
    console.log("Calling geboOpenDocumentJS(" + url + ")")
    var _request = new XMLHttpRequest();
    _request.myUrl = url;
    _request.myContentType = contentType;
    _request.myIframe = iframeElement;
    _request.open("GET", url, true);
    _request.setRequestHeader("Authorization", "Bearer " + token);
    _request.responseType = 'blob';
    _request.onreadystatechange = function () {
        console.log("Calling geboOpenDocumentJS(" + this.myUrl + ").onreadystatechange(" + this.status + ")");




    };
    _request.onloadend = function () {
        console.log("Calling geboOpenDocumentJS(" + this.myUrl + ").oloadend(" + this.status + ")");
        if (this.myIframe) {
            console.log("Try to display:" + this.myUrl);
            var blob = new Blob([this.response], { type: this.myContentType });
            this.myIframe.srcdoc="<html><body></body></html>";
            var url = URL.createObjectURL(blob);
            this.myIframe.contentDocument.location=(url);
        }else {
            console.error("Null iframe passed");
        }
    };
    _request.send();

}