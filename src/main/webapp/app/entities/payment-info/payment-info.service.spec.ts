/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import PaymentInfoService from './payment-info.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { PaymentInfo } from '@/shared/model/payment-info.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('PaymentInfo Service', () => {
    let service: PaymentInfoService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new PaymentInfoService();
      currentDate = new Date();
      elemDefault = new PaymentInfo('ABC', 'AAAAAAA', currentDate, 'WAITING_PAYMENT', 'PG', 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            paymentCompletedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find('ABC').then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a PaymentInfo', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ABC',
            paymentCompletedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            paymentCompletedDate: currentDate,
          },
          returnedFromService,
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a PaymentInfo', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a PaymentInfo', async () => {
        const returnedFromService = Object.assign(
          {
            orderNumber: 'BBBBBB',
            paymentCompletedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            paymentMethodType: 'BBBBBB',
            pgTID: 1,
            pgStatus: 1,
            pgDetail: 'BBBBBB',
            payer: 'BBBBBB',
            payerPhone: 'BBBBBB',
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            paymentCompletedDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a PaymentInfo', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a PaymentInfo', async () => {
        const patchObject = Object.assign(
          {
            status: 'BBBBBB',
            pgTID: 1,
          },
          new PaymentInfo(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            paymentCompletedDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a PaymentInfo', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of PaymentInfo', async () => {
        const returnedFromService = Object.assign(
          {
            orderNumber: 'BBBBBB',
            paymentCompletedDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            paymentMethodType: 'BBBBBB',
            pgTID: 1,
            pgStatus: 1,
            pgDetail: 'BBBBBB',
            payer: 'BBBBBB',
            payerPhone: 'BBBBBB',
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            paymentCompletedDate: currentDate,
          },
          returnedFromService,
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of PaymentInfo', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a PaymentInfo', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a PaymentInfo', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
