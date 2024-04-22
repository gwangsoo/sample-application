/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FileInfoDetails from './file-info-details.vue';
import FileInfoService from './file-info.service';
import AlertService from '@/shared/alert/alert.service';

type FileInfoDetailsComponentType = InstanceType<typeof FileInfoDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const fileInfoSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('FileInfo Management Detail Component', () => {
    let fileInfoServiceStub: SinonStubbedInstance<FileInfoService>;
    let mountOptions: MountingOptions<FileInfoDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      fileInfoServiceStub = sinon.createStubInstance<FileInfoService>(FileInfoService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          fileInfoService: () => fileInfoServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        fileInfoServiceStub.find.resolves(fileInfoSample);
        route = {
          params: {
            fileInfoId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(FileInfoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.fileInfo).toMatchObject(fileInfoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        fileInfoServiceStub.find.resolves(fileInfoSample);
        const wrapper = shallowMount(FileInfoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
