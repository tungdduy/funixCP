export class ServiceResult {
  data: any;
  status: 'success' | 'error';
  message: string;

  error(message: string): ServiceResult {
    this.message = message;
    this.status = "error";
    return this;
  }

  success(data: any): ServiceResult {
    this.data = data;
    this.status = "success";
    return this;
  }

  isSuccess(): boolean {
    return this.status === "success";
  }
}
