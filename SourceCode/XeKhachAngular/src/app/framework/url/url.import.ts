export const UrlImport = {
    "CHECK_IN.FORGOT_PASSWORD-component": () => require('../../business/pages/check-in/forgot-password/forgot-password.component').ForgotPasswordComponent,
    "CHECK_IN.REGISTER-component": () => require('../../business/pages/check-in/register/register.component').RegisterComponent,
    "CHECK_IN-component": () => require('../../business/pages/check-in/check-in.component').CheckInComponent,
    "ADMIN-component": () => require('../../business/pages/admin/admin.component').AdminComponent,
    "CHECK_IN.LOGIN-component": () => require('../../business/pages/check-in/login/login.component').LoginComponent,
    "ADMIN.BUSS_STAFF-component": () => require('../../business/pages/admin/buss-staff/buss-staff.component').BussStaffComponent,
    "CHECK_IN-module": () => import('../../business/pages/check-in/check-in.module').then(m => m.CheckInModule),
    "ADMIN-module": () => import('../../business/pages/admin/admin.module').then(m => m.AdminModule),
};
