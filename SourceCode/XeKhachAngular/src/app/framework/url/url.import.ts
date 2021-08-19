export const UrlImport = {
    // @ts-ignore
    "CHECK_IN.REGISTER-component": () => require('app/business/pages/check-in/register/register.component').RegisterComponent,
    // @ts-ignore
    "ADMIN-component": () => require('app/business/pages/admin/admin.component').AdminComponent,
    // @ts-ignore
    "ADMIN.EMPLOYEE-component": () => require('app/business/pages/admin/employee/employee.component').EmployeeComponent,
    // @ts-ignore
    "ADMIN.TICKET-component": () => require('app/business/pages/admin/ticket/ticket.component').TicketComponent,
    // @ts-ignore
    "ADMIN.COMPANY_MANAGER-component": () => require('app/business/pages/admin/company-manager/company-manager.component').CompanyManagerComponent,
    // @ts-ignore
    "ADMIN.BUSS-component": () => require('app/business/pages/admin/buss/buss.component').BussComponent,
    // @ts-ignore
    "CHECK_IN.FORGOT_PASSWORD-component": () => require('app/business/pages/check-in/forgot-password/forgot-password.component').ForgotPasswordComponent,
    // @ts-ignore
    "ADMIN.BUSS_TYPE-component": () => require('app/business/pages/admin/buss-type/buss-type.component').BussTypeComponent,
    // @ts-ignore
    "ADMIN.MY_COMPANY-component": () => require('app/business/pages/admin/my-company/my-company.component').MyCompanyComponent,
    // @ts-ignore
    "CHECK_IN-component": () => require('app/business/pages/check-in/check-in.component').CheckInComponent,
    // @ts-ignore
    "CHECK_IN.LOGIN-component": () => require('app/business/pages/check-in/login/login.component').LoginComponent,
    // @ts-ignore
    "ADMIN.ALL_USER-component": () => require('app/business/pages/admin/all-user/all-user.component').AllUserComponent,
    // @ts-ignore
    "ADMIN.BUSS_POINT-component": () => require('app/business/pages/admin/buss-point/buss-point.component').BussPointComponent,
    // @ts-ignore
    "CHECK_IN-module": () => import('app/business/pages/check-in/check-in.module').then(m => m.CheckInModule),
    // @ts-ignore
    "ADMIN-module": () => import('app/business/pages/admin/admin.module').then(m => m.AdminModule),
    // @ts-ignore
    "ADMIN.MY_ACCOUNT-component": () => require('app/business/pages/admin/my-account/my-account.component').MyAccountComponent,
    // @ts-ignore
    "CHECK_IN.LOGOUT-component": () => require('app/business/pages/check-in/logout/logout.component').LogoutComponent,
    // @ts-ignore
    "ADMIN.MY_TRIP-component": () => require('app/business/pages/admin/my-trip/my-trip.component').MyTripComponent,
};
