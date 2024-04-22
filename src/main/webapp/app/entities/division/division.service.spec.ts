/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import DivisionService from './division.service';
import { Division } from '@/shared/model/division.model';

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
  describe('Division Service', () => {
    let service: DivisionService;
    let elemDefault;

    beforeEach(() => {
      service = new DivisionService();
      elemDefault = new Division(
        'ABC',
        0,
        'AAAAAAA',
        0,
        0,
        false,
        0,
        0,
        false,
        'PERFECT',
        'TEAM4',
        'FIRST',
        false,
        'RATING',
        false,
        'CARD_RATING',
        0,
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
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

      it('should create a Division', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ABC',
          },
          elemDefault,
        );
        const expected = Object.assign({}, returnedFromService);

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Division', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Division', async () => {
        const returnedFromService = Object.assign(
          {
            seq: 1,
            name: 'BBBBBB',
            ratingRuleTeamMin: 1,
            ratingRuleTeamMax: 1,
            ratingRuleIndividualLimit: true,
            ratingRuleIndividualMin: 1,
            ratingRuleIndividualMax: 1,
            entryLimit: true,
            roundRobinRankingDecisionType: 'BBBBBB',
            roundRobinGroupType: 'BBBBBB',
            nextRoundDecisionType: 'BBBBBB',
            roundRoginThirdDecision: true,
            thirdDecisionRankingRule: 'BBBBBB',
            useAllRoundSameFormat: true,
            eventRangeType: 'BBBBBB',
            eliminationTeamCount: 1,
          },
          elemDefault,
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Division', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Division', async () => {
        const patchObject = Object.assign(
          {
            seq: 1,
            name: 'BBBBBB',
            ratingRuleIndividualMax: 1,
            nextRoundDecisionType: 'BBBBBB',
            thirdDecisionRankingRule: 'BBBBBB',
            useAllRoundSameFormat: true,
            eventRangeType: 'BBBBBB',
          },
          new Division(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Division', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Division', async () => {
        const returnedFromService = Object.assign(
          {
            seq: 1,
            name: 'BBBBBB',
            ratingRuleTeamMin: 1,
            ratingRuleTeamMax: 1,
            ratingRuleIndividualLimit: true,
            ratingRuleIndividualMin: 1,
            ratingRuleIndividualMax: 1,
            entryLimit: true,
            roundRobinRankingDecisionType: 'BBBBBB',
            roundRobinGroupType: 'BBBBBB',
            nextRoundDecisionType: 'BBBBBB',
            roundRoginThirdDecision: true,
            thirdDecisionRankingRule: 'BBBBBB',
            useAllRoundSameFormat: true,
            eventRangeType: 'BBBBBB',
            eliminationTeamCount: 1,
          },
          elemDefault,
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Division', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Division', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Division', async () => {
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
