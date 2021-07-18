export const UrlImport = {
    // @ts-ignore
    "CHECK_IN.FORGOT_PASSWORD-component": () => require('app/business/pages/check-in/forgot-password/forgot-password.component').ForgotPasswordComponent,
    // @ts-ignore
    "CHECK_IN.REGISTER-component": () => require('app/business/pages/check-in/register/register.component').RegisterComponent,
    // @ts-ignore
    "CHECK_IN-component": () => require('app/business/pages/check-in/check-in.component').CheckInComponent,
    // @ts-ignore
    "ADMIN-component": () => require('app/business/pages/admin/admin.component').AdminComponent,
    // @ts-ignore
    "CHECK_IN.LOGIN-component": () => require('app/business/pages/check-in/login/login.component').LoginComponent,
    // @ts-ignore
    "ADMIN.BUSS_STAFF-component": () => require('app/business/pages/admin/buss-staff/buss-staff.component').BussStaffComponent,
    // @ts-ignore
    "CHECK_IN-module": () => import('app/business/pages/check-in/check-in.module').then(m => m.CheckInModule),
    // @ts-ignore
    "ADMIN-module": () => import('app/business/pages/admin/admin.module').then(m => m.AdminModule),
};
