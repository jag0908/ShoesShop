<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp"%>

<section>
  <%@ include file="sub_image_menu.jsp" %>
  <article>
    <form>
      <h2>회사 소개</h2>
      <div class="field" style="border-top: 1px dotted #006493;">
        <label>회사소개</label><div>&nbsp;</div>
      </div>
      <div class="field" style="border-top: 1px dotted #006493;">
        <label>회사연혁</label><div>&nbsp;</div>
      </div>
      <div class="field" style="border-top: 1px dotted #006493;">
        <label>교통편</label><div>&nbsp;</div>
      </div>
      <div class="field" style="border-top: 1px dotted #006493; height: 600px">
          <label>지도</label>
          <div>
            <div id="daumRoughmapContainer1754032170516" class="root_daum_roughmap root_daum_roughmap_landing" style="display: flex; flex-direction: column"></div>
            <script charset="UTF-8" class="daum_roughmap_loader_script" src="https://ssl.daumcdn.net/dmaps/map_js_init/roughmapLoader.js"></script>
            <script charset="UTF-8">
              new daum.roughmap.Lander({
                "timestamp" : "1754032170516",
                "key" : "6mrjhqc8c6g",
                "mapWidth" : "640",
                "mapHeight" : "500"
              }).render();
            </script>
          </div>
      </div>
    </form>
  </article>
</section>

<%@ include file="../footer.jsp"%>