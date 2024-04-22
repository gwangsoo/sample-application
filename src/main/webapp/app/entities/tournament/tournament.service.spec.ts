/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import TournamentService from './tournament.service';
import { Tournament } from '@/shared/model/tournament.model';

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
  describe('Tournament Service', () => {
    let service: TournamentService;
    let elemDefault;

    beforeEach(() => {
      service = new TournamentService();
      elemDefault = new Tournament(
        'ABC',
        0,
        'AAAAAAA',
        0,
        false,
        false,
        'AUTO',
        'ROUNDROBIN',
        'RANDOM',
        'SINGLE',
        false,
        'CARD_RATING',
        false,
        0,
        'AUTO',
        'ALL',
        'NO',
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

      it('should create a Tournament', async () => {
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

      it('should not create a Tournament', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Tournament', async () => {
        const returnedFromService = Object.assign(
          {
            seq: 1,
            name: 'BBBBBB',
            day: 1,
            eventTournament: true,
            entryClose: true,
            entryApprovalType: 'BBBBBB',
            tournamentType: 'BBBBBB',
            seedingRule: 'BBBBBB',
            tournamentPlayMode: 'BBBBBB',
            thirdPlaceDecision: true,
            divisionRule: 'BBBBBB',
            entryLimit: true,
            numOfEntry: 1,
            divisionAssignMethod: 'BBBBBB',
            entryGenderType: 'BBBBBB',
            handicap: 'BBBBBB',
          },
          elemDefault,
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Tournament', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Tournament', async () => {
        const patchObject = Object.assign(
          {
            seq: 1,
            eventTournament: true,
            entryApprovalType: 'BBBBBB',
            tournamentPlayMode: 'BBBBBB',
            divisionRule: 'BBBBBB',
            entryLimit: true,
            divisionAssignMethod: 'BBBBBB',
            entryGenderType: 'BBBBBB',
          },
          new Tournament(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Tournament', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Tournament', async () => {
        const returnedFromService = Object.assign(
          {
            seq: 1,
            name: 'BBBBBB',
            day: 1,
            eventTournament: true,
            entryClose: true,
            entryApprovalType: 'BBBBBB',
            tournamentType: 'BBBBBB',
            seedingRule: 'BBBBBB',
            tournamentPlayMode: 'BBBBBB',
            thirdPlaceDecision: true,
            divisionRule: 'BBBBBB',
            entryLimit: true,
            numOfEntry: 1,
            divisionAssignMethod: 'BBBBBB',
            entryGenderType: 'BBBBBB',
            handicap: 'BBBBBB',
          },
          elemDefault,
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Tournament', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Tournament', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Tournament', async () => {
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
