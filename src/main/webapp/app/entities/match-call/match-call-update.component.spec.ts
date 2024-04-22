/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import MatchCallUpdate from './match-call-update.vue';
import MatchCallService from './match-call.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import MatchTeamService from '@/entities/match-team/match-team.service';

type MatchCallUpdateComponentType = InstanceType<typeof MatchCallUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const matchCallSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MatchCallUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('MatchCall Management Update Component', () => {
    let comp: MatchCallUpdateComponentType;
    let matchCallServiceStub: SinonStubbedInstance<MatchCallService>;

    beforeEach(() => {
      route = {};
      matchCallServiceStub = sinon.createStubInstance<MatchCallService>(MatchCallService);
      matchCallServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          matchCallService: () => matchCallServiceStub,
          matchTeamService: () =>
            sinon.createStubInstance<MatchTeamService>(MatchTeamService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(MatchCallUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(MatchCallUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchCall = matchCallSample;
        matchCallServiceStub.update.resolves(matchCallSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchCallServiceStub.update.calledWith(matchCallSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        matchCallServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MatchCallUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.matchCall = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(matchCallServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        matchCallServiceStub.find.resolves(matchCallSample);
        matchCallServiceStub.retrieve.resolves([matchCallSample]);

        // WHEN
        route = {
          params: {
            matchCallId: '' + matchCallSample.id,
          },
        };
        const wrapper = shallowMount(MatchCallUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.matchCall).toMatchObject(matchCallSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        matchCallServiceStub.find.resolves(matchCallSample);
        const wrapper = shallowMount(MatchCallUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
