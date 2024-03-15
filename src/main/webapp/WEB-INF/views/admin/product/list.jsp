<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/tags.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" ></script>
<title>bootstrap</title>
</head>
<body>
<%@ include file="../../common/navbar.jsp" %>
<div class="container">
	<div class="row mb-3">
		<div class="col-12">
			<h1>상품 관리</h1>
		</div>
	</div>
	<div class="row mb-3">
		<div class="col-3">
			<div class="card">
				<div class="card-header">관리자 메뉴</div>
				<div class="list-group list-group-flush">
					<a href="/admin/user/list" class="list-group-item list-group-item-action">사용자 관리</a>
					<a href="/admin/product/list" class="list-group-item list-group-item-action active">상품 관리</a>
					<a href="" class="list-group-item list-group-item-action">주문 관리</a>
					<a href="" class="list-group-item list-group-item-action">결재 관리</a>
					<a href="" class="list-group-item list-group-item-action">공지사항 관리</a>
				</div>
			</div>
		</div>
		<div class="col-9">
			<h3>상품 목록 <a href="create" class="btn btn-primary btn-sm float-end">신규 상품 등록</a></h3>
			<table class="table">
				<colgroup>
					<col width="10%">
					<col width="15%">
					<col width="45%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
				</colgroup>
				<thead>
					<tr>
						<th>순번</th>
						<th>카테고리</th>
						<th>상품이름</th>
						<th>제조사</th>
						<th>가격</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="product" items="${products }" varStatus="status">
						<tr>
							<td>${status.count }</td>						
							<td><span id="pro-cat-${product.no }">${product.category.name}</span></td>						
							<td><span id="pro-name-${product.no }">${product.name }</span></td>						
							<td><span id="pro-company-${product.no }">${product.company.name }</span></td>						
							<td><span id="pro-price-${product.no }"><fmt:formatNumber value="${product.price }"/></span>원</td>
								<td><button class="btn btn-outline-primary btn-sm" onclick="showProductForm(${product.no})">수정하기</button></td>						
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

</div>
<!-- 
<div class="modal" tabindex="-1" id="modal-product-info">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <h5 class="modal-title">상품 상세정보</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
         </div>
         <div class="modal-body">
            <table class="table">
               <tbody>
                  <tr>
                     <th>상품번호</th>
                     <td><span id="product-no"></span></td>
                     <th>상품이름</th>
                     <td><span id="product-name"></span></td>
                  </tr>
                  <tr>
                     <th>상품설명</th>
                     <td><span id="product-description"></span></td>
                     <th>상품 수량</th>
                     <td><span id="product-stock"></span></td>
                  </tr>
                  <tr>
                     <th>상품상태</th>
                     <td><span id="product-status"></span></td>
                     <th>상품제조사</th>
                     <td><span id="product-company"></span></td>
                  </tr>
                  <tr>
                     <th>상품등록</th>
                     <td colspan="3"><span id="product-createdDate"></span></td>
                  </tr>
               </tbody>
            </table>
         </div>
         <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
         </div>
      </div>
   </div>
</div>
 -->
  <div class="modal model-lg"  id="modal-product-form">
    <div class="modal-dialog">
       <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">상품정보 수정폼</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
         </div>
             <div class="modal-body">
                <form class="border bg-light p-3">
           		  <sec:csrfInput/>
                  <input type="hidden" name="no"> <!--  내가 클릭한 상품이 들어가도록 -->
                  <div class="row mb-3">
                     <div class="col-6">
                        <div class="form-group">
                           <label class="form-label">상위 카테고리</label>
                           <select class="form-select" name="parentCategoryNo" onchange="changeCategory()">
                              <c:forEach var="cat" items="${productCategories}">
                           <option value="${cat.no }">${cat.name }</option>                           
                              </c:forEach>
                           </select>
                        </div>
                     </div>
                     <div class="col-6">
                        <div class="form-group">
                           <label class="form-label">하위 카테고리</label>
                           <select class="form-select" name="categoryNo">
                           
                           </select>
                        </div>
                     </div>
                  </div>
                  <div class="row mb-3">
                     <div class="col-6">
                        <div class="form-group">
                           <label class="form-label">상품명</label>
                           <input type="text" class="form-control" name="name" />
                        </div>
                     </div>
                     <div class="col-6">
                        <div class="form-group">
                           <label class="form-label">제조사</label>
                           <select class="form-select" name="companyNo">
                              <c:forEach var="company" items="${companies }">
                           <option value="${company.no }">${company.name }</option>                              
                              </c:forEach>
                           </select>
                        </div>
                     </div>
                  </div>
                  <div class="row mb-3">
                     <div class="col-6">
                        <div class="form-group">
                           <label class="form-label">가격</label>
                           <input type="text" class="form-control" name="price" />
                        </div>
                     </div>
                     <div class="col-6">
                        <div class="form-group">
                           <label class="form-label">수량</label>
                           <input type="text" class="form-control" name="stock" />
                        </div>
                     </div>
                  </div>
                  <div class="row mb-3">
                     <div class="form-group">
                        <label class="form-label">가격</label>
                        <textarea class="form-control" rows="3" name="description"></textarea>
                     </div>
                  </div>
               </form>
             </div>
             <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                <button type="button" class="btn btn-primary" onclick="submitForm()">수정</button>
             </div>
          </div>
       </div>
    </div>
 
 
<script type="text/javascript">
		const productFormModal = new bootstrap.Modal("#modal-product-form");
		
		   async function showProductForm(productNo) {
		   setFormValue(productNo);
		   
		   productFormModal.show();
		   
		}
		
		   async function setFormValue(productNo) {
			   
		   let response = await fetch("/admin/product/" + productNo);
		   let product = await response.json();
			
		   setCategory(product.category.parentNo, product.category.no);
		   document.querySelector("input[name=no]").value = product.no;
		   document.querySelector("input[name=name]").value = product.name;
		   document.querySelector("select[name=companyNo]").value = product.company.no;
		   document.querySelector("input[name=price]").value = product.price;
		   document.querySelector("input[name=stock]").value = product.stock;
		   document.querySelector("textarea[name=description]").value = product.description;
		   
		}
		
		async function setCategory(parentNo, no) {
			   document.querySelector("select[name=parentCategoryNo]").value =parentNo; //상위카테고리
			   
			   let response = await fetch("/admin/category?catNo=" + parentNo);
			   let subCategories = await response.json();
				
			   // 하위카테고리 확인
			   let options ="";
			   for(let index= 0; index < subCategories.length; index++) {
				   let subCategory = subCategories[index];
				   options += `<option value="\${subCategory.no}"\${subCategory.no == no? "selected" : ""}> \${subCategory.name} </option>`
			   }
			   document.querySelector("select[name=categoryNo]").innerHTML = options;
		}
		
		function changeCategory() {
			let parentNo = document.querySelector("select[name=parentCategoryNo]").value
			setCategory(parentNo);
		}
		
		async function submitForm() {
			// 자바스크립트 객체를 생성한것
			let data = {
				no: document.querySelector("input[name=no]").value,
				category: {
							no:document.querySelector("select[name=categoryNo]").value
				},
				name: document.querySelector("input[name=name]").value,
				company:{
							no: document.querySelector("select[name=companyNo]").value
				},
				price: document.querySelector("input[name=price]").value,
				stock: document.querySelector("input[name=stock]").value,
				description: document.querySelector("textarea[name=description]").value
					
			};
			let jsonText = JSON.stringify(data);
				
			// modify로 보내준다.
			let response = await fetch("/admin/product/modify", {
				method: "POST",
				// 요청메세지의 헤더정보적는거
				headers : {
					"Content-Type" : "application/json",
					"X-CSRF-TOKEN" : document.querySelector("input[name='_csrf']").value
				},
				//json방식으로 바꾼것
				body: jsonText
				
			})
			if (response.ok) {
				
				document.getElementById("pro-name-" + data.no).textContent = data.name;
				document.getElementById("pro-price-" + data.no).textContent = parseInt(data.price).toLocaleString();
				
				// select박스 엘리먼트를 선택한다.
				let categorySelect = document.querySelector("select[name=categoryNo]");
				// select.options는 셀러터박스의 모든 옵션을 배열로 반환한다.
				// select.selectIndex는 셀렉터박스의 옵션 중에서 현재 선택된 옵션의  index를 반환한다.
				// select.options[select.selectIndex]는 셀렉터박스의 옵션 중에서 현재 선택된 옵션 엘리먼트를 반환한다.
				//select.options[select.selectIndex].textContent는 현재 선택된 옵션 엘리먼트의 텍스트 컨텐츠를 반환한다.
				let categoryName = categorySelect.options[categorySelect.selectedIndex].textContent;
				document.getElementById("pro-cat-" + data.no).textContent = categoryName;
				
				let companySelect = document.querySelector("select[name=companyNo]");
				let companyName = companySelect.options[companySelect.selectedIndex].textContent;
				document.getElementById("pro-company-" + data.no).textContent = companyName;
				
				productFormModal.hide();
			}
		}
		
			


/*
 * 
	const myModal = new bootstrap.Modal(document.getElementById('modal-product-info'));
	
	function showProductInfo(no) {
		let xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status ==200) {
				let text = xhr.responseText;
				// 전송받은 text를 let product = JSON.parse(text);실행으로 배열/객체로 변경해준다.
				let product = JSON.parse(text);
				
				document.getElementById("product-no").textContent = product.no;
				document.getElementById("product-name").textContent = product.name;
				document.getElementById("product-description").textContent = product.description;
				document.getElementById("product-stock").textContent = product.stock;
				document.getElementById("product-status").textContent = product.status;
				document.getElementById("product-company").textContent = product.company.name;
				document.getElementById("product-createdDate").textContent = product.createdDate;
			
				myModal.show();
			
			}
		}
		xhr.open("GET", "/admin/product/" + no);
		xhr.send(null)
	}
*/
</script>
 
</body>
</html>