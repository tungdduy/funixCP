export const UrlImport = {
    // @ts-ignore
    "CHECK_IN.REGISTER-component": () => require('app/business/pages/check-in/register/register.component').RegisterComponent,
    // @ts-ignore
    "ADMIN.BUSS_STOP-component": () => require('app/business/pages/admin/buss-stop/buss-stop.component').BussStopComponent,
    // @ts-ignore
    "ADMIN-component": () => require('app/business/pages/admin/admin.component').AdminComponent,
    // @ts-ignore
    "ADMIN.CALLER_EMPLOYEE-component": () => require('app/business/pages/admin/caller-employee/caller-employee.component').CallerEmployeeComponent,
    // @ts-ignore
    "ADMIN.BUSS_EMPLOYEE-component": () => require('app/business/pages/admin/buss-employee/buss-employee.component').BussEmployeeComponent,
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
    "CHECK_IN-component": () => require('app/business/pages/check-in/check-in.component').CheckInComponent,
    // @ts-ignore
    "CHECK_IN.LOGIN-component": () => require('app/business/pages/check-in/login/login.component').LoginComponent,
    // @ts-ignore
    "CHECK_IN-module": () => import('app/business/pages/check-in/check-in.module').then(m => m.CheckInModule),
    // @ts-ignore
    "ADMIN-module": () => import('app/business/pages/admin/admin.module').then(m => m.AdminModule),
    // @ts-ignore
    "ADMIN.MY_ACCOUNT-component": () => require('app/business/pages/admin/my-account/my-account.component').MyAccountComponent,
    // @ts-ignore
    "ADMIN.MY_TRIP-component": () => require('app/business/pages/admin/my-trip/my-trip.component').MyTripComponent,
};
