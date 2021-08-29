# Đồ án tốt nghiệp Funix

* Lộ trình triển khai [xem tại đây!](https://docs.google.com/document/d/e/2PACX-1vTA-_X0syWMzsYMxA9L-8euIXqw7MJXZ6jJicpN6AYUUHivKDBKOgrZT_fQUWgW7g/pub)


# Bảng theo dõi tiến độ tài liệu
* Bảng sau đây dành riêng cho theo dõi công việc liên quan tới tài liệu báo cáo.
* Giai đoạn coding sẽ tạo branch riêng cho từng thành viên.

### Tùng
Tên công việc | Thuộc báo cáo | Dự Kiến | Thực Tế | Github Link
------------  | ------------- |-------- | ------- | -----------
Deployment Diagram |  | 30/05/2021     | 30/05/2021 | [link](https://raw.githubusercontent.com/tungdduy/funixCP/main/Documents/working/Tung/Deployment%20Diagram)


### Minh
Tên công việc | Thuộc báo cáo | Dự Kiến | Thực Tế | Github Link
------------  | ------------- |-------- | ------- | -----------
Điền nội dung | điền nội dung | điền nội dung     | điền nội dung

### Giáp
Tên công việc | Thuộc báo cáo | Dự Kiến | Thực Tế | Github Link
------------  | ------------- |-------- | ------- | -----------
vẽ usecase + flow charts    | 3.5           | 25/05/2021  | 27/05/2021    |[đây](https://htmlpreview.github.io/?https://github.com/tungdduy/funixCP/blob/main/Documents/working/Giap/usecases_and_flow_charts/uc_flow.html)
 database tables   |           | 30/05/2021  | 29/05/2021    |[đây](https://htmlpreview.github.io/?https://github.com/tungdduy/funixCP/blob/main/Documents/working/Giap/database_tables/db_tables.html)

Note:
- Deployment command:
  java  -Xms128m -Xmx256m -Dlogging.config=file:/home/funix/config/log4j2-app.xml -jar XeKhachSpring.jar --spring.config.location=file:///home/funix/config/application.yml
  
- The app service:
  systemctl [start|stop|status] myapp