
export const UrlImport = {
  // ------ START IMPORT SECTION
  // ******************* IMPORT_SECTION_1 *******************
  "CHECK_IN-module": () => import('app/business/pages/check-in/check-in.module').then(m => m.CheckInModule),
  "CHECK_IN-component": () => require('app/business/pages/check-in/check-in.component').CheckInComponent,
  "CHECK_IN.LOGIN-component": () => require('app/business/pages/check-in/login/login.component').LoginComponent,
  "CHECK_IN.REGISTER-component": () => require('app/business/pages/check-in/register/register.component').RegisterComponent,
  "CHECK_IN.FORGOT_PASSWORD-component": () => require('app/business/pages/check-in/forgot-password/forgot-password.component').ForgotPasswordComponent,
  // ******************* IMPORT_SECTION_1 *******************
  // ------- END IMPORT SECTION
};
